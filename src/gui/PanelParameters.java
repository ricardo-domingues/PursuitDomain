package gui;

import ga.geneticOperators.MutationMUTATION_NAME;
import ga.geneticOperators.Recombination;
import ga.geneticOperators.RecombinationOneCut;
import ga.geneticOperators.RecombinationTwoCuts;
import ga.geneticOperators.RecombinationUniform;
import ga.selectionMethods.SelectionMethod;
import ga.selectionMethods.Tournament;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JTextField;
import pursuitDomain.PredatorIndividual;
import pursuitDomain.PursuitDomainProblem;

public class PanelParameters extends PanelAtributesValue {

    public static final int TEXT_FIELD_LENGHT = 7;
    public static final String SEED = "1";
    public static final String POPULATION_SIZE = "100";
    public static final String GENERATIONS = "100";
    public static final String TOURNAMENT_SIZE = "2";
    public static final String PROB_RECOMBINATION = "0.7";
    public static final String PROB_MUTATION = "0.1";
    JTextField jTextFieldSeed = new JTextField(SEED, TEXT_FIELD_LENGHT);
    JTextField jTextFieldN = new JTextField(POPULATION_SIZE, TEXT_FIELD_LENGHT);
    JTextField jTextFieldGenerations = new JTextField(GENERATIONS, TEXT_FIELD_LENGHT);
    String[] selectionMethods = {"Tournament"};
    JComboBox jComboBoxSelectionMethods = new JComboBox(selectionMethods);
    JTextField jTextFieldTournamentSize = new JTextField(TOURNAMENT_SIZE, TEXT_FIELD_LENGHT);
    String[] recombinationMethods = {"One cut", "Two cuts", "Uniform"};
    JComboBox jComboBoxRecombinationMethods = new JComboBox(recombinationMethods);
    JTextField jTextFieldProbRecombination = new JTextField(PROB_RECOMBINATION, TEXT_FIELD_LENGHT);
    JTextField jTextFieldProbMutation = new JTextField(PROB_MUTATION, TEXT_FIELD_LENGHT);
    JComboBox jComboBoxSelectionController = new JComboBox(new String[]{"Random Controller","Ad-hoc Controller","Single Neural Network","Multiple Neural Network"});
    //MORE PARAMETERS?
    
    public PanelParameters() {
        title = "Genetic algorithm parameters";
        
        labels.add(new JLabel("Controller method: "));
        valueComponents.add(jComboBoxSelectionController);
        jComboBoxSelectionController.addActionListener(new jComboBoxSelectionController_ActionAdapter(this));
        
        labels.add(new JLabel("Seed: "));
        valueComponents.add(jTextFieldSeed);
        jTextFieldSeed.addKeyListener(new IntegerTextField_KeyAdapter(null));

        labels.add(new JLabel("Population size: "));
        valueComponents.add(jTextFieldN);
        jTextFieldN.addKeyListener(new IntegerTextField_KeyAdapter(null));

        labels.add(new JLabel("# of generations: "));
        valueComponents.add(jTextFieldGenerations);
        jTextFieldGenerations.addKeyListener(new IntegerTextField_KeyAdapter(null));

        labels.add(new JLabel("Selection method: "));
        valueComponents.add(jComboBoxSelectionMethods);
        jComboBoxSelectionMethods.addActionListener(new JComboBoxSelectionMethods_ActionAdapter(this));

        labels.add(new JLabel("Tournament size: "));
        valueComponents.add(jTextFieldTournamentSize);
        jTextFieldTournamentSize.addKeyListener(new IntegerTextField_KeyAdapter(null));

        labels.add(new JLabel("Recombination method: "));
        valueComponents.add(jComboBoxRecombinationMethods);

        labels.add(new JLabel("Recombination prob.: "));
        valueComponents.add(jTextFieldProbRecombination);

        labels.add(new JLabel("Mutation prob.: "));
        valueComponents.add(jTextFieldProbMutation);

        //MORE PARAMETERS?
        
        configure();
    }
    

    public void actionPerformedSelectionMethods(ActionEvent e) {
        jTextFieldTournamentSize.setEnabled(jComboBoxSelectionMethods.getSelectedIndex() == 0);
    }

    public SelectionMethod<PredatorIndividual, PursuitDomainProblem> getSelectionMethod() {
        switch (jComboBoxSelectionMethods.getSelectedIndex()) {
            case 0:
                return new Tournament<>(
                        Integer.parseInt(jTextFieldN.getText()),
                        Integer.parseInt(jTextFieldTournamentSize.getText()));
        }
        return null;
    }

    public Recombination<PredatorIndividual> getRecombinationMethod() {

        double recombinationProb = Double.parseDouble(jTextFieldProbRecombination.getText());

        switch (jComboBoxRecombinationMethods.getSelectedIndex()) {
            case 0:
                return new RecombinationOneCut<>(recombinationProb);
            case 1:
                return new RecombinationTwoCuts<>(recombinationProb);
            case 2:
                return new RecombinationUniform<>(recombinationProb);
        }
        return null;
    }

    public MutationMUTATION_NAME<PredatorIndividual> getMutationMethod() {
        double mutationProbability = Double.parseDouble(jTextFieldProbMutation.getText());
        //COMPLETE?
        return new MutationMUTATION_NAME<>(mutationProbability/*COMPLETE?*/);
    }
}

class JComboBoxSelectionMethods_ActionAdapter implements ActionListener {

    final private PanelParameters adaptee;

    JComboBoxSelectionMethods_ActionAdapter(PanelParameters adaptee) {
        this.adaptee = adaptee;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        adaptee.actionPerformedSelectionMethods(e);
    }
}

class jComboBoxSelectionController_ActionAdapter implements ActionListener {

    final private PanelParameters adaptee;

    jComboBoxSelectionController_ActionAdapter(PanelParameters adaptee) {
        this.adaptee = adaptee;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        adaptee.actionPerformedSelectionMethods(e);
    }
}

class IntegerTextField_KeyAdapter implements KeyListener {

    final private MainFrame adaptee;

    IntegerTextField_KeyAdapter(MainFrame adaptee) {
        this.adaptee = adaptee;
    }

    @Override
    public void keyReleased(KeyEvent e) {
    }

    @Override
    public void keyPressed(KeyEvent e) {
    }

    @Override
    public void keyTyped(KeyEvent e) {
        char c = e.getKeyChar();
        if (!Character.isDigit(c) || c == KeyEvent.VK_BACK_SPACE || c == KeyEvent.VK_DELETE) {
            e.consume();
        }
    }
}
