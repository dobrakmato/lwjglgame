import eu.matejkormuth.game.shared.GameComponent;

class LookAtComponent extends GameComponent {
    void initialize() {
        println "LookAtComponent has been initialized!";
    };
    
    void update(float delta) {
        println "LookAtComponent has been updated!";
    };
}