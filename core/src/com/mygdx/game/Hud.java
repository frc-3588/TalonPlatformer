import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.graphics.g2d.BitmapFont;


public class Hud {
    private int keyCount;
    private Player player;
    private int lives;

    private Label keyLabel;
    private Label livesLabel;

    //keycount, lives, pause screen
    public Hud (Player player)
    {
            Table table = new Table();
            Table.top();
            
            keyCount = 1;
            this.player = player;
            lives = player.getLives();
            keyLabel = new Label("KEYS", new Label.Labelstyle(new Bitmapfont(), Color.YELLOW));
            livesLabel = new Label("LIVES", new Label.Labelstyle(new Bitmapfont(), Color.WHITE));
            
            table.add(keyLabel).expandX().padTop(10);
            table.add(livesLabel).expandX().padTop(10);

    
    }
    public void update(float dt){
            
            }
}