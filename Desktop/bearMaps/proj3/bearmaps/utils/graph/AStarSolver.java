package bearmaps.utils.graph;
import bearmaps.utils.pq.DoubleMapPQ;
import edu.princeton.cs.algs4.Stopwatch;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Stack;

    public class AStarSolver<Vertex>  implements ShortestPathsSolver<Vertex> {
        private SolverOutcome outcome;
        private double solutionWeight;
        private List<Vertex> solution = new LinkedList<>();
        private double timeSpent;
        private int statesnumber;

        public AStarSolver(AStarGraph<Vertex> input, Vertex start, Vertex end, double timeout) {
            HashMap<Vertex, Double> distance = new HashMap<>();
            HashMap<Vertex, Vertex> previous = new HashMap<>();
            HashSet<Vertex> visited = new HashSet<>();
            Stopwatch sw = new Stopwatch();
            DoubleMapPQ map = new DoubleMapPQ();
            map.insert(start, input.estimatedDistanceToGoal(start, end));
            distance.put(start, 0.0);
            previous.put(start, start);
            Stack<Vertex> reverse = new Stack<>();
            timeSpent = 0;
            while (map.size() != 0 && !map.peek().equals(end) && timeSpent < timeout) {
                Vertex current = (Vertex) map.poll();
                visited.add(current);
                statesnumber += 1;
                List<WeightedEdge<Vertex>> neighbors = input.neighbors(current);
                for (int i = 0; i < neighbors.size(); i++) {
                    WeightedEdge interested = neighbors.get(i);
                    if (visited.contains(interested.to())) {
                        continue;
                    } else if (!map.contains(interested.to())) {
                        distance.put((Vertex) interested.to(), (distance.get(interested.from()) + interested.weight()));
                        previous.put((Vertex) interested.to(), current);
                        map.insert(interested.to(), (distance.get(interested.from()) + interested.weight()) + input.estimatedDistanceToGoal((Vertex) interested.to(), end));
                    } else if ((distance.get(interested.from()) + interested.weight()) < distance.get(interested.to())) {
                        distance.put((Vertex) interested.to(), distance.get(interested.from()) + interested.weight());
                        map.changePriority(interested.to(), distance.get(interested.from()) + interested.weight() + input.estimatedDistanceToGoal((Vertex) interested.to(), end));
                        previous.put((Vertex) interested.to(), current);

                    }
                }
                timeSpent = sw.elapsedTime();
            }
            if (timeSpent >= timeout) {
                outcome = SolverOutcome.TIMEOUT;
                solution = null;
                solutionWeight = 0;
            } else if (map.size() == 0) {
                outcome = SolverOutcome.UNSOLVABLE;
                solution = null;
                solutionWeight = 0;
            } else {
                outcome = SolverOutcome.SOLVED;
                solutionWeight = distance.get(end);
                Vertex record = end;
                while (!record.equals(start)) {
                    reverse.push(record);
                    record = previous.get(record);
                }
                reverse.push(start);
                while (!reverse.isEmpty()) {
                    solution.add(reverse.pop());
                }

            }


        }

        public SolverOutcome outcome() {
            return outcome;
        }

        @Override
        public List<Vertex> solution() {
            return solution;
        }

        @Override
        public double solutionWeight() {
            return solutionWeight;
        }

        @Override
        public int numStatesExplored() {
            return statesnumber;
        }

        @Override
        public double explorationTime() {
            return timeSpent;
        }
    }

