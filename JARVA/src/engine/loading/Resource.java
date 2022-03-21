package engine.loading;

import org.newdawn.slick.Image;
import org.newdawn.slick.Sound;
import org.newdawn.slick.loading.DeferredResource;
import org.newdawn.slick.loading.LoadingList;

import ui.display.images.ImageManager;
import ui.sound.SoundManager;

import java.io.File;

public class Resource implements DeferredResource {
    private File file;

    public Resource(File file) { this.file = file; }

    public String getDescription() { return file.getName(); }
    public void load() {
        String path = file.getPath();

        // FileName: split[0], FileExtension: split[1]
        String[] split = file.getName().split("\\.");

        // Turning off deferred loading temporarily, as deferred loading with sounds causes issues
        LoadingList.setDeferredLoading(false);
        try {
            switch (split[1].toLowerCase()) {
                case "png":
                    ImageManager.addImage(split[0], new Image(path));
                    break;
                
                case "ogg":
                    SoundManager.addSound(split[0], new Sound(path));
                    break;
                    
                default:
                    System.out.println("File Type not Recognized");
                    break;
            }
        } 
        catch(Exception e) {  System.out.println("Failed to load file"); } 
        finally { LoadingList.setDeferredLoading(true); }
    }
}