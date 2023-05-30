package sample;

import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.text.DecimalFormat;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.stream.IntStream;

public class Controller implements Initializable {

    public TitledPane tilted1, tilted2, tilted3, tilted4, tilted5, tilted6, tilted7;
    public Circle circle1, circle2, circle3, circle4, circle5, circle6, circle7;
    public ImageView comp_huff, comp_lz77, comp_lz78, comp_lzw, comp_mtf, comp_interval, comp_rle;
    public ImageView decomp_huff, decomp_lz77, decomp_lz78, decomp_lzw, decomp_mtf, decomp_interval, decomp_rle;
    public ImageView english, lithuanian, image_menu;
    public CheckBox huff_check, check_all;
    public CheckBox LZ77_check;
    public CheckBox LZ78_check;
    public CheckBox LZW_check;
    public CheckBox MTF_check;
    public CheckBox Interval_check;
    public CheckBox RLE_check;
    public MenuBar myMenuBar;
    public Label my_status, file_name, file_size, time_huff, dtime_huff, time_lzw, dtime_lzw, time_lz78, dtime_lz78, time_lz77, dtime_lz77, time_mtf, dtime_mtf, time_interval, dtime_interval, time_rle, dtime_rle;
    public ChoiceBox choice1, choice2 ,choice2_2 ,choice3, choice4, choice5, choice5_2, choice5_3, choice6, choice6_2, choice7, choice7_2;
    public Button reset, start, choose;
    public Image img_loading, img_ok;
    public String absolute_path=null, absolute_name;
    public Label data_huff, data_lz77, data_lz78, data_lzw, data_mtf, data_interval, data_rle;




    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        // make TiltedPane non collapsible
        tilted1.setCollapsible(false);
        tilted2.setCollapsible(false);
        tilted3.setCollapsible(false);
        tilted4.setCollapsible(false);
        tilted5.setCollapsible(false);
        tilted6.setCollapsible(false);
        tilted7.setCollapsible(false);

        // set english flag in menu
        Image image_british_flag = new Image("images/uk_square.png");
        english.setImage(image_british_flag);
        image_menu.setImage(image_british_flag);
        Image image_lithuanian_flag = new Image("images/lithuania_square.png");
        lithuanian.setImage(image_lithuanian_flag);

        // create tooltip for info icons
        Tooltip tooltip = new Tooltip("Time Complexity: Very fast\n\n"+
                                        "Code-word length in Huffman coding stands for the length of code-word \n"+
                                        "which is cut into bits. The default length of code-word is 8 bits.\n" +
                                        "Choosing 16 bits means that file will be broken down into pieces of 16 bits.\n"+
                                        "Usually for basic text files 8 bit / 16 bit or multiple of 8 length is the best one.\n" +
                                        "Although, when dealing with more monotonic files such as 1 color .bmp file\n"+
                                        "the best practice would be to use the highest possible bit length (16 in our case).");
        tooltip.setShowDelay(Duration.millis(100));
        tooltip.setShowDuration(Duration.seconds(59));
        tooltip.getStyleClass().add("ttip");
        Tooltip.install(circle1, tooltip);

        Tooltip tooltip2 = new Tooltip("Time Complexity: Medium\n\n"+
                                        "Offset size - the distance of the pointer from the look-ahead buffer.\n"+
                                        "Match length size - the number of consecutive symbols in the search buffer\n"+
                                        "that match consecutive symbols in the look-ahead buffer, starting with the first symbol.\n"+
                                        "For any basic file the best practise is to use the default values (offset: 12, match length: 4)\n"+
                                        "For more monotonic files, such as single color .bmp file parameters such as (o:12, l:12) might\n"+
                                        "compress file by more than 99% of it's original size. NOTE: Choosing some values might result\n"+
                                        "in corrupted files or infinite loop (in this case press Reset button)");
        tooltip2.setShowDelay(Duration.millis(100));
        tooltip2.setShowDuration(Duration.seconds(59));
        tooltip2.getStyleClass().add("ttip2");
        Tooltip.install(circle2, tooltip2);

        Tooltip tooltip3 = new Tooltip("Time Complexity: Very slow\n\n"+
                "Table size k in LZ78 algorithm stand for the size of the table 2^k. If our chosen parameter is 8\n"+
                "then then table in this algorithm will be only updated to 256. After this number is reached,\n"+
                "the program will be only using codes from this 256 size table. The default size 0 is the same\n"+
                "as size 32, meaning that both 0 and 32 (theoretically) has unlimited table size (Integer.MAX_VALUE or 2^32).\n"+
                "NOTE: sizes 2-8 were only made for testing purpose. Choosing size 4, means that code-words will be cut\n"+
                "into 2 bits, therefore using this parameter is impractical. 'Compressed' file will be always expanded.\n"+
                "For compression it is recommended to use files <1M as this algorithm is quite slow.");
        tooltip3.setShowDelay(Duration.millis(100));
        tooltip3.setShowDuration(Duration.seconds(59));
        tooltip3.getStyleClass().add("ttip3");
        Tooltip.install(circle3, tooltip3);


        Tooltip tooltip4 = new Tooltip("Time Complexity: Fast\n\n"+
                                        "Table size k in LZW algorithm stand for the size of the table 2^k. If our chosen parameter is 8\n"+
                                        "then then table in this algorithm will be only updated to 256. After this number is reached,\n"+
                                        "the program will be only using codes from this 256 size table. The default size 0 is the same\n"+
                                        "as size 32, meaning that both 0 and 32 (theoretically) has unlimited table size (Integer.MAX_VALUE or 2^32).\n"+
                                        "NOTE: sizes 2-8 were only made for testing purpose. Choosing size 4, means that code-words will be cut\n"+
                                        "into 2 bits, therefore using this parameter is impractical. 'Compressed' file will be always expanded.");
        tooltip4.setShowDelay(Duration.millis(100));
        tooltip4.setShowDuration(Duration.seconds(59));
        tooltip4.getStyleClass().add("ttip4");
        Tooltip.install(circle4, tooltip4);


        Tooltip tooltip5 = new Tooltip("Time Complexity: Fast\n\n"+
                                        "Code-word length (or just Length in this case) stands for the length of code-word \n"+
                                        "which is cut into bits. The default length of code-word is 8 bits.\n" +
                                        "Choosing 16 bits means that file will be broken down into pieces of 16 bits.\n"+
                                        "VLC stands for Variable-length codes which is 4 different codes that are used\n" +
                                        "to compress non-negative Integers constructed by Move-to-front algorithm\n"+
                                        "Threshold is a point at which newly found symbol is moved in the Alphabet list.\n"+
                                        "Default value is 1, meaning that new symbol will be moved to the position 0\n"+
                                        "(traditional MTF algorithm). For more info, read about 'Sticky MTF'");
        tooltip5.setShowDelay(Duration.millis(100));
        tooltip5.setShowDuration(Duration.seconds(59));
        tooltip5.getStyleClass().add("ttip4");
        Tooltip.install(circle5, tooltip5);

        Tooltip tooltip6 = new Tooltip("Time Complexity: Medium\n\n"+
                                        "Code-word length (or just Length in this case) stands for the length of code-word \n"+
                                        "which is cut into bits. The default length of code-word is 8 bits.\n" +
                                        "Choosing 16 bits means that file will be broken down into pieces of 16 bits.\n"+
                                        "VLC stands for Variable-length codes which is 4 different codes that are used\n" +
                                        "to compress non-negative Integers constructed by Move-to-front algorithm");
        tooltip6.setShowDelay(Duration.millis(100));
        tooltip6.setShowDuration(Duration.seconds(59));
        tooltip6.getStyleClass().add("ttip2");
        Tooltip.install(circle6, tooltip6);

        Tooltip tooltip7 = new Tooltip("Time Complexity: Fast\n\n"+
                                        "Code-word length (or just Length in this case) stands for the length of code-word \n"+
                                        "which is cut into bits. The default length of code-word is 8 bits.\n" +
                                        "Choosing 16 bits means that file will be broken down into pieces of 16 bits.\n"+
                                        "VLC stands for Variable-length codes which is 4 different codes that are used\n" +
                                        "to compress non-negative Integers constructed by Move-to-front algorithm");
        tooltip7.setShowDelay(Duration.millis(100));
        tooltip7.setShowDuration(Duration.seconds(59));
        tooltip7.getStyleClass().add("ttip4");
        Tooltip.install(circle7, tooltip7);

        // load gifs for compression
        //Image image = new Image("images/loading.gif");
        Image image = new Image("images/red_false.png");
        comp_huff.setImage(image);
        comp_lz77.setImage(image);
        comp_lz78.setImage(image);
        comp_lzw.setImage(image);
        comp_mtf.setImage(image);
        comp_interval.setImage(image);
        comp_rle.setImage(image);

        // load gifs for decompression
        decomp_huff.setImage(image);
        decomp_lz77.setImage(image);
        decomp_lz78.setImage(image);
        decomp_lzw.setImage(image);
        decomp_mtf.setImage(image);
        decomp_interval.setImage(image);
        decomp_rle.setImage(image);

        // checkboxes
        dealingWithCheckBox();

        // choiceboxes
        IntStream.rangeClosed(1,16).boxed().forEach(choice1.getItems()::add);
        choice1.setValue(8);
        IntStream.rangeClosed(1,20).boxed().forEach(choice2.getItems()::add);
        IntStream.rangeClosed(1,20).boxed().forEach(choice2_2.getItems()::add);
        choice2.setValue(12);
        choice2_2.setValue(4);
        IntStream.rangeClosed(0,32).boxed().forEach(choice3.getItems()::add);
        choice3.getItems().remove(1);
        choice3.setValue(0);
        IntStream.rangeClosed(0,32).boxed().forEach(choice4.getItems()::add);
        // choice4: remove 1 cuz it's worthless and set default value = 0
        choice4.getItems().remove(1);
        choice4.setValue(0);

        IntStream.rangeClosed(1,16).boxed().forEach(choice5.getItems()::add);
        List<String> VLC = Arrays.asList("fib", "gamma", "delta", "lev");
        ObservableList<String> obList = FXCollections.observableList(VLC);
        choice5_2.getItems().clear();
        choice5_2.setItems(obList);
        choice5_2.setValue("fib");
        choice5.setValue(8);
        IntStream.rangeClosed(1,100).boxed().forEach(choice5_3.getItems()::add);
        choice5_3.setValue(1);

        IntStream.rangeClosed(1,16).boxed().forEach(choice6.getItems()::add);
        obList = FXCollections.observableList(VLC);
        choice6_2.getItems().clear();
        choice6_2.setItems(obList);
        choice6_2.setValue("fib");
        choice6.setValue(8);

        IntStream.rangeClosed(1,16).boxed().forEach(choice7.getItems()::add);
        obList = FXCollections.observableList(VLC);
        choice7_2.getItems().clear();
        choice7_2.setItems(obList);
        choice7_2.setValue("fib");
        choice7.setValue(8);

        check_all.selectedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                huff_check.setSelected(newValue);
                LZ77_check.setSelected(newValue);
                LZ78_check.setSelected(newValue);
                LZW_check.setSelected(newValue);
                MTF_check.setSelected(newValue);
                Interval_check.setSelected(newValue);
                RLE_check.setSelected(newValue);
            }
        });

    }

    public void dealingWithCheckBox(){
        huff_check.selectedProperty().addListener((observable, oldValue, newValue) -> {
            tilted1.setDisable(!newValue);
            comp_huff.setVisible(newValue);
            decomp_huff.setVisible(newValue);
        });

        LZ77_check.selectedProperty().addListener((observable, oldValue, newValue) -> {
            tilted2.setDisable(!newValue);
            comp_lz77.setVisible(newValue);
            decomp_lz77.setVisible(newValue);
        });

        LZ78_check.selectedProperty().addListener((observable, oldValue, newValue) -> {
            tilted3.setDisable(!newValue);
            comp_lz78.setVisible(newValue);
            decomp_lz78.setVisible(newValue);
        });

        LZW_check.selectedProperty().addListener((observable, oldValue, newValue) -> {
            tilted4.setDisable(!newValue);
            comp_lzw.setVisible(newValue);
            decomp_lzw.setVisible(newValue);
        });

        MTF_check.selectedProperty().addListener((observable, oldValue, newValue) -> {
            tilted5.setDisable(!newValue);
            comp_mtf.setVisible(newValue);
            decomp_mtf.setVisible(newValue);
        });

        Interval_check.selectedProperty().addListener((observable, oldValue, newValue) -> {
            tilted6.setDisable(!newValue);
            comp_interval.setVisible(newValue);
            decomp_interval.setVisible(newValue);
        });

        RLE_check.selectedProperty().addListener((observable, oldValue, newValue) -> {
            tilted7.setDisable(!newValue);
            comp_rle.setVisible(newValue);
            decomp_rle.setVisible(newValue);
        });
    }

    // open help scene
    public void menuPressed(ActionEvent actionEvent) throws IOException {
        Parent help = FXMLLoader.load(getClass().getResource("sample_help.fxml"));
        Scene sceneHelp = new Scene(help, 1000, 700);
        Stage window = (Stage) myMenuBar.getScene().getWindow();
        window.setScene(sceneHelp);
        window.show();
    }

    public void toChooseFile(ActionEvent actionEvent) {
        // call of a file chooser dialog
        FileChooser fc = new FileChooser();
        fc.setTitle("Choose any file");
        File f = fc.showOpenDialog(null);
        if(f!=null) {
            StringBuilder sb = new StringBuilder();
            try(BufferedReader br = new BufferedReader(new FileReader(f))){
                String line;
                while((line = br.readLine()) != null)
                    sb.append(line).append('\n');
                br.close();
                absolute_path = f.getAbsolutePath();
                absolute_name = f.getName();
                //file_name.setText(f.getAbsolutePath());
                file_name.setText(f.getName());
                file_size.setText(f.length() + " bytes / " + new DecimalFormat("##.##").format((double)f.length()/ 1024) + " kb / "+ new DecimalFormat("##.###").format((double)f.length()/ (1024*1024))  + " mb");
                my_status.setText("File was read");
            }catch(IOException e){
                //log.appendText("Unsuccessful reading of file" + '\n');
                my_status.setText("Unsuccessful reading of file");
            }
        }else{
            //log.appendText("File wasn't chosen" + '\n');
            my_status.setText("File wasn't chosen");
        }
    }

    public void onStart(ActionEvent actionEvent) {

        // Before compression
        start.setText("");
        img_loading = new Image("images/loading.gif");
        img_ok = new Image("images/green_true.png");
        ImageView view = new ImageView(img_loading);
        view.setFitHeight(20);
        view.setFitWidth(20);
        view.setPreserveRatio(true);
        start.setGraphic(view);

        disableThings();

        // upload loading gifs
        if(huff_check.isSelected()) {
            comp_huff.setImage(img_loading);
            decomp_huff.setImage(img_loading);
        }

        if(LZ77_check.isSelected()){
            comp_lz77.setImage(img_loading);
            decomp_lz77.setImage(img_loading);
        }
        if(LZ78_check.isSelected()){
            comp_lz78.setImage(img_loading);
            decomp_lz78.setImage(img_loading);
        }
        if(LZW_check.isSelected()){
            comp_lzw.setImage(img_loading);
            decomp_lzw.setImage(img_loading);
        }
        if(MTF_check.isSelected()){
            comp_mtf.setImage(img_loading);
            decomp_mtf.setImage(img_loading);
        }
        if(Interval_check.isSelected()){
            comp_interval.setImage(img_loading);
            decomp_interval.setImage(img_loading);
        }
        if(RLE_check.isSelected()){
            comp_rle.setImage(img_loading);
            decomp_rle.setImage(img_loading);
        }

        // do our stuff

        // let's use Task for our main algorithms
        // We use Task for big operations and Platform.runLater(...) for smaller ones
        Task task = new Task<Void>() {
            @Override public Void call() {
                // status progress
                /*Platform.runLater(() -> {
                    String progress = "Compressing and Decompressing";
                    my_status.setText(progress);
                    for(int i=0; i<3; i++){
                        progress = progress+".";
                        my_status.setText(progress);
                        if(i == 2)
                            progress = "Compressing and Decompressing";
                    }
                });*/

                if(huff_check.isSelected()){
                    huffman();
                }
                if(LZ77_check.isSelected()){
                    lz77();
                }

                if(LZ78_check.isSelected()){
                    lz78();
                }

                if(LZW_check.isSelected()){
                    lzw();
                }

                if(MTF_check.isSelected()){
                    mtf();
                }

                if(Interval_check.isSelected()){
                    interval();
                }

                if(RLE_check.isSelected()){
                    rle();
                }

                Platform.runLater(() -> {
                    my_status.setText("Compression and Decompression DONE");
                });
                view.setImage(img_ok);
                start.setGraphic(view);

                return null;
            }
        };

        ProgressBar bar = new ProgressBar();
        bar.progressProperty().bind(task.progressProperty());
        new Thread(task).start();


        // After compression
        reset.setDisable(false);

    }

    public void disableThings(){
        huff_check.setDisable(true);
        LZ77_check.setDisable(true);
        LZ78_check.setDisable(true);
        LZW_check.setDisable(true);
        MTF_check.setDisable(true);
        Interval_check.setDisable(true);
        RLE_check.setDisable(true);
        check_all.setDisable(true);

        choice1.setDisable(true);
        choice2.setDisable(true);
        choice2_2.setDisable(true);
        choice3.setDisable(true);
        choice4.setDisable(true);
        choice5.setDisable(true);
        choice5_2.setDisable(true);
        choice5_3.setDisable(true);
        choice6.setDisable(true);
        choice6_2.setDisable(true);
        choice7.setDisable(true);
        choice7_2.setDisable(true);

        reset.setDisable(true);
        choose.setDisable(true);
        start.setDisable(true);
    }

    public void huffman(){
        // starting huffman coding
        long start = System.nanoTime();
        EncodeHuffman e = new EncodeHuffman();
        e.start(absolute_path, (byte)Integer.parseInt(choice1.getValue().toString()));
        long finish = System.nanoTime();
        long timeElapsed = finish - start;
        Platform.runLater(() -> {
            time_huff.setText("("+new DecimalFormat("##.#").format((double)timeElapsed / 1_000_000_000.0) + " sec.)");
        });


        comp_huff.setImage(img_ok);

        // start huffman decoding
        start = System.nanoTime();
        DecodeHuffman d = new DecodeHuffman();
        d.start(absolute_name);
        finish = System.nanoTime();
        long timeElapsed2 = finish - start;
        Platform.runLater(() -> {
            dtime_huff.setText("("+new DecimalFormat("##.#").format((double)timeElapsed2 / 1_000_000_000.0) + " sec.)");
        });
        decomp_huff.setImage(img_ok);

        Platform.runLater(() -> {
            //edit label for compressed file
            File file, original_file;
            file = new File("src/encoded/encode.huf");
            original_file = new File(absolute_path);
            //data_huff.setText(file.length() + " Bytes ("+ file.length()/1024+" kb)");

            // calculate how much our file shrunk/expanded in %
            double ans = ((((double)original_file.length() - file.length())/original_file.length())*100);
            if(ans>0) {
                data_huff.setTextFill(Color.GREEN);
                data_huff.setText(file.length() + " b\n" + new DecimalFormat("##.##").format((double) file.length() / 1024) + " kb\n" + "Shrunk by: \n" +
                        "" + new DecimalFormat("##.##").format(ans) + "%");
            }else{
                ans = ((((double)file.length()-original_file.length())/original_file.length())*100);
                data_huff.setTextFill(Color.RED);
                data_huff.setText(file.length() + " b\n" + new DecimalFormat("##.##").format((double) file.length() / 1024) + " kb\n" + "Expanded by: \n" +
                        "" + new DecimalFormat("##.##").format(ans) + "%");
            }
        });
    }

    public void lzw(){
        // starting lzw coding
        long start = System.nanoTime();
        EncodeLZW e = new EncodeLZW();
        e.start(absolute_path, (byte)Integer.parseInt(choice4.getValue().toString()));
        long finish = System.nanoTime();
        long timeElapsed = finish - start;
        Platform.runLater(() -> {
            time_lzw.setText("("+new DecimalFormat("##.#").format((double)timeElapsed / 1_000_000_000.0) + " sec.)");
        });


        comp_lzw.setImage(img_ok);

        // start lzw decoding
        start = System.nanoTime();
        DecodeLZW d = new DecodeLZW();
        d.start(absolute_name, absolute_path);
        finish = System.nanoTime();
        long timeElapsed2 = finish - start;
        Platform.runLater(() -> {
            dtime_lzw.setText("("+new DecimalFormat("##.#").format((double)timeElapsed2 / 1_000_000_000.0) + " sec.)");
        });
        decomp_lzw.setImage(img_ok);

        Platform.runLater(() -> {
            //edit label for compressed file
            File file, original_file;
            file = new File("src/encoded/encode.lzw");
            original_file = new File(absolute_path);
            //data_huff.setText(file.length() + " Bytes ("+ file.length()/1024+" kb)");

            // calculate how much our file shrunk/expanded in %
            double ans = ((((double)original_file.length() - file.length())/original_file.length())*100);
            if(ans>0) {
                data_lzw.setTextFill(Color.GREEN);
                data_lzw.setText(file.length() + " b\n" + new DecimalFormat("##.##").format((double) file.length() / 1024) + " kb\n" + "Shrunk by: \n" +
                        "" + new DecimalFormat("##.##").format(ans) + "%");
            }else{
                ans = ((((double)file.length()-original_file.length())/original_file.length())*100);
                data_lzw.setTextFill(Color.RED);
                data_lzw.setText(file.length() + " b\n" + new DecimalFormat("##.##").format((double) file.length() / 1024) + " kb\n" + "Expanded by: \n" +
                        "" + new DecimalFormat("##.##").format(ans) + "%");
            }
        });
    }

    public void lz77(){
        // starting lz77 coding
        long start = System.nanoTime();
        EncodeLZ77 e = new EncodeLZ77();
        e.start(absolute_path, Integer.parseInt(choice2.getValue().toString()), Integer.parseInt(choice2_2.getValue().toString()));
        long finish = System.nanoTime();
        long timeElapsed = finish - start;
        Platform.runLater(() -> {
            time_lz77.setText("("+new DecimalFormat("##.#").format((double)timeElapsed / 1_000_000_000.0) + " sec.)");
        });


        comp_lz77.setImage(img_ok);

        // start huffman decoding
        start = System.nanoTime();
        DecodeLZ77 d = new DecodeLZ77();
        d.start(absolute_name);
        finish = System.nanoTime();
        long timeElapsed2 = finish - start;
        Platform.runLater(() -> {
            dtime_lz77.setText("("+new DecimalFormat("##.#").format((double)timeElapsed2 / 1_000_000_000.0) + " sec.)");
        });
        decomp_lz77.setImage(img_ok);

        Platform.runLater(() -> {
            //edit label for compressed file
            File file, original_file;
            file = new File("src/encoded/encode.lz77");
            original_file = new File(absolute_path);
            //data_huff.setText(file.length() + " Bytes ("+ file.length()/1024+" kb)");

            // calculate how much our file shrunk/expanded in %
            double ans = ((((double)original_file.length() - file.length())/original_file.length())*100);
            if(ans>0) {
                data_lz77.setTextFill(Color.GREEN);
                data_lz77.setText(file.length() + " b\n" + new DecimalFormat("##.##").format((double) file.length() / 1024) + " kb\n" + "Shrunk by: \n" +
                        "" + new DecimalFormat("##.##").format(ans) + "%");
            }else{
                ans = ((((double)file.length()-original_file.length())/original_file.length())*100);
                data_lz77.setTextFill(Color.RED);
                data_lz77.setText(file.length() + " b\n" + new DecimalFormat("##.##").format((double) file.length() / 1024) + " kb\n" + "Expanded by: \n" +
                        "" + new DecimalFormat("##.##").format(ans) + "%");
            }
        });
    }

    public void lz78(){
        // starting lz78 coding
        long start = System.nanoTime();
        EncodeLZ78 e = new EncodeLZ78();
        e.start(absolute_path, (byte)Integer.parseInt(choice3.getValue().toString()));
        long finish = System.nanoTime();
        long timeElapsed = finish - start;
        Platform.runLater(() -> {
            time_lz78.setText("("+new DecimalFormat("##.#").format((double)timeElapsed / 1_000_000_000.0) + " sec.)");
        });


        comp_lz78.setImage(img_ok);

        // start lz78 decoding
        start = System.nanoTime();
        DecodeLZ78 d = new DecodeLZ78();
        d.start(absolute_name);
        finish = System.nanoTime();
        long timeElapsed2 = finish - start;
        Platform.runLater(() -> {
            dtime_lz78.setText("("+new DecimalFormat("##.#").format((double)timeElapsed2 / 1_000_000_000.0) + " sec.)");
        });
        decomp_lz78.setImage(img_ok);

        Platform.runLater(() -> {
            //edit label for compressed file
            File file, original_file;
            file = new File("src/encoded/encode.lz78");
            original_file = new File(absolute_path);
            //data_huff.setText(file.length() + " Bytes ("+ file.length()/1024+" kb)");

            // calculate how much our file shrunk/expanded in %
            double ans = ((((double)original_file.length() - file.length())/original_file.length())*100);
            if(ans>0) {
                data_lz78.setTextFill(Color.GREEN);
                data_lz78.setText(file.length() + " b\n" + new DecimalFormat("##.##").format((double) file.length() / 1024) + " kb\n" + "Shrunk by: \n" +
                        "  " + new DecimalFormat("##.##").format(ans) + "%");
            }else{
                ans = ((((double)file.length()-original_file.length())/original_file.length())*100);
                data_lz78.setTextFill(Color.RED);
                data_lz78.setText(file.length() + " b\n" + new DecimalFormat("##.##").format((double) file.length() / 1024) + " kb\n" + "Expanded by: \n" +
                        "" + new DecimalFormat("##.##").format(ans) + "%");
            }
        });
    }

    public void mtf(){
        // starting mtf coding
        long start = System.nanoTime();
        EncodeMTF e = new EncodeMTF();
        e.setThreshold(Integer.parseInt(choice5_3.getValue().toString()));
        e.start(absolute_path, (byte)Integer.parseInt(choice5.getValue().toString()), choice5_2.getValue().toString());
        long finish = System.nanoTime();
        long timeElapsed = finish - start;
        Platform.runLater(() -> {
            time_mtf.setText("("+new DecimalFormat("##.#").format((double)timeElapsed / 1_000_000_000.0) + " sec.)");
        });


        comp_mtf.setImage(img_ok);

        // start mtf decoding
        start = System.nanoTime();
        DecodeMTF d = new DecodeMTF();
        d.start(absolute_name);
        finish = System.nanoTime();
        long timeElapsed2 = finish - start;
        Platform.runLater(() -> {
            dtime_mtf.setText("("+new DecimalFormat("##.#").format((double)timeElapsed2 / 1_000_000_000.0) + " sec.)");
        });
        decomp_mtf.setImage(img_ok);

        Platform.runLater(() -> {
            //edit label for compressed file
            File file, original_file;
            file = new File("src/encoded/encode.mtf");
            original_file = new File(absolute_path);
            //data_huff.setText(file.length() + " Bytes ("+ file.length()/1024+" kb)");

            // calculate how much our file shrunk/expanded in %
            double ans = ((((double)original_file.length() - file.length())/original_file.length())*100);
            if(ans>0) {
                data_mtf.setTextFill(Color.GREEN);
                data_mtf.setText(file.length() + " b\n" + new DecimalFormat("##.##").format((double) file.length() / 1024) + " kb\n" + "Shrunk by: \n" +
                        "  " + new DecimalFormat("##.##").format(ans) + "%");
            }else{
                ans = ((((double)file.length()-original_file.length())/original_file.length())*100);
                data_mtf.setTextFill(Color.RED);
                data_mtf.setText(file.length() + " b\n" + new DecimalFormat("##.##").format((double) file.length() / 1024) + " kb\n" + "Expanded by: \n" +
                        "" + new DecimalFormat("##.##").format(ans) + "%");
            }
        });
    }

    public void interval(){
        // starting mtf coding
        long start = System.nanoTime();
        EncodeInterval e = new EncodeInterval();
        e.start(absolute_path, (byte)Integer.parseInt(choice6.getValue().toString()), choice6_2.getValue().toString());
        long finish = System.nanoTime();
        long timeElapsed = finish - start;
        Platform.runLater(() -> {
            time_interval.setText("("+new DecimalFormat("##.#").format((double)timeElapsed / 1_000_000_000.0) + " sec.)");
        });


        comp_interval.setImage(img_ok);

        // start mtf decoding
        start = System.nanoTime();
        DecodeInterval d = new DecodeInterval();
        d.start(absolute_name);
        finish = System.nanoTime();
        long timeElapsed2 = finish - start;
        Platform.runLater(() -> {
            dtime_interval.setText("("+new DecimalFormat("##.#").format((double)timeElapsed2 / 1_000_000_000.0) + " sec.)");
        });
        decomp_interval.setImage(img_ok);

        Platform.runLater(() -> {
            //edit label for compressed file
            File file, original_file;
            file = new File("src/encoded/encode.int");
            original_file = new File(absolute_path);
            //data_huff.setText(file.length() + " Bytes ("+ file.length()/1024+" kb)");

            // calculate how much our file shrunk/expanded in %
            double ans = ((((double)original_file.length() - file.length())/original_file.length())*100);
            if(ans>0) {
                data_interval.setTextFill(Color.GREEN);
                data_interval.setText(file.length() + " b\n" + new DecimalFormat("##.##").format((double) file.length() / 1024) + " kb\n" + "Shrunk by: \n" +
                        "  " + new DecimalFormat("##.##").format(ans) + "%");
            }else{
                ans = ((((double)file.length()-original_file.length())/original_file.length())*100);
                data_interval.setTextFill(Color.RED);
                data_interval.setText(file.length() + " b\n" + new DecimalFormat("##.##").format((double) file.length() / 1024) + " kb\n" + "Expanded by: \n" +
                        "" + new DecimalFormat("##.##").format(ans) + "%");
            }
        });
    }

    public void rle(){
        // starting mtf coding
        long start = System.nanoTime();
        EncodeRLE e = new EncodeRLE();
        e.start(absolute_path, (byte)Integer.parseInt(choice7.getValue().toString()), choice7_2.getValue().toString());
        long finish = System.nanoTime();
        long timeElapsed = finish - start;
        Platform.runLater(() -> {
            time_rle.setText("("+new DecimalFormat("##.#").format((double)timeElapsed / 1_000_000_000.0) + " sec.)");
        });


        comp_rle.setImage(img_ok);

        // start mtf decoding
        start = System.nanoTime();
        DecodeRLE d = new DecodeRLE();
        d.start(absolute_name);
        finish = System.nanoTime();
        long timeElapsed2 = finish - start;
        Platform.runLater(() -> {
            dtime_rle.setText("("+new DecimalFormat("##.#").format((double)timeElapsed2 / 1_000_000_000.0) + " sec.)");
        });
        decomp_rle.setImage(img_ok);

        Platform.runLater(() -> {
            //edit label for compressed file
            File file, original_file;
            file = new File("src/encoded/encode.rlen");
            original_file = new File(absolute_path);
            //data_huff.setText(file.length() + " Bytes ("+ file.length()/1024+" kb)");

            // calculate how much our file shrunk/expanded in %
            double ans = ((((double)original_file.length() - file.length())/original_file.length())*100);
            if(ans>0) {
                data_rle.setTextFill(Color.GREEN);
                data_rle.setText(file.length() + " b\n" + new DecimalFormat("##.##").format((double) file.length() / 1024) + " kb\n" + "Shrunk by: \n" +
                        "  " + new DecimalFormat("##.##").format(ans) + "%");
            }else{
                ans = ((((double)file.length()-original_file.length())/original_file.length())*100);
                data_rle.setTextFill(Color.RED);
                data_rle.setText(file.length() + " b\n" + new DecimalFormat("##.##").format((double) file.length() / 1024) + " kb\n" + "Expanded by: \n" +
                        "" + new DecimalFormat("##.##").format(ans) + "%");
            }
        });
    }

    public void onReset(ActionEvent actionEvent) throws IOException {
        Locale locale = new Locale("en_LT");
        ResourceBundle bundle = ResourceBundle.getBundle("resources.translations", locale);
        Parent help = FXMLLoader.load(getClass().getResource("sample.fxml"), bundle);
        Scene sceneHelp = new Scene(help, 1000, 850);
        Stage window = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        sceneHelp.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
        window.setScene(sceneHelp);
        window.show();
    }

    // change flag of menu
    public void menu_en(ActionEvent actionEvent) {
        Image image_british_flag = new Image("images/uk_square.png");
        image_menu.setImage(image_british_flag);
    }

    public void menu_lt(ActionEvent actionEvent) {
        Image image_lithuanian_flag = new Image("images/lithuania_square.png");
        image_menu.setImage(image_lithuanian_flag);
    }


    public void onDialog(ActionEvent actionEvent) {
        //Creating a dialog
        Dialog<String> dialog = new Dialog<String>();
        //Setting the title
        dialog.setTitle("About");
        ButtonType type = new ButtonType("Ok", ButtonBar.ButtonData.OK_DONE);
        //Setting the content of the dialog
        dialog.setContentText("© Program was made by Simas Gardauskas\n\n Vilnius University");
        //Adding buttons to the dialog pane
        dialog.getDialogPane().getButtonTypes().add(type);
        dialog.showAndWait();
    }
}