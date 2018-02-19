package Utils;

import javafx.animation.FadeTransition;
import javafx.animation.Interpolator;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.util.Duration;

public class navigation {

    private final String login="/src/Presentation/Login.fxml";
    private final String home="/Presentation/Home.fxml";
    private final String dashboard="/Presentation/Dashboard.fxml";
    private final String database="/Presentation/Database.fxml";
    private final String uangMasuk="/Presentation/UangMasuk.fxml";
    private final String tambahUangMasuk="/Presentation/TambahUangMasuk.fxml";
    private final String ubahUangMasuk="/Presentation/UbahUangMasuk.fxml";
    private final String user="/Presentation/User.fxml";
    private final String uangKeluar="/Presentation/UangKeluar.fxml";
    private final String tambahUangKeluar="/Presentation/TambahUangKeluar.fxml";
    private final String ubahUangKeluar="/Presentation/UbahUangKeluar.fxml";
    private final String userManagement="/Presentation/UserManagement.fxml";
    private final String tambahUserManagement="/Presentation/TambahUserManagement.fxml";
    private final String ubahUserManagement="/Presentation/UbahUserManagement.fxml";
    private final String laporanUangMasukHarian="/Presentation/LaporanUangMasukHarian.fxml";
    private final String laporanUangMasukBulanan="/Presentation/LaporanUangMasukBulanan.fxml";
    private final String laporanUangKeluarHarian="/Presentation/LaporanUangKeluarHarian.fxml";
    private final String laporanUangKeluarBulanan="/Presentation/LaporanUangKeluarBulanan.fxml";
    private String username,nama,email;
    
    public Image applicationIcon = new Image(getClass().getResourceAsStream("/src/Images/souk.png"));
    
    public String getHome(){
        return home;
    }
    
    public String getLogin(){
        return login;
    }
    
    public String getDashboard(){
        return dashboard;
    }
    
    public String getDatabase(){
        return database;
    }
    
    public String getUangMasuk(){
        return uangMasuk;
    }
    
    public String getTambahUangMasuk(){
        return tambahUangMasuk;
    }
    
    public String getUbahUangMasuk(){
        return ubahUangMasuk;
    }
    
    public String getTambahUangKeluar(){
        return tambahUangKeluar;
    }
    
    public String getUbahUangKeluar(){
        return ubahUangKeluar;
    }
    
    public String getUserManagement(){
        return userManagement;
    }
    
    public String getTambahUserManagement(){
        return tambahUserManagement;
    }
    
    public String getUbahUserManagement(){
        return ubahUserManagement;
    }
    
    public String getLaporanUangMasukHarian(){
        return laporanUangMasukHarian;
    }
    
    public String getLaporanUangMasukBulanan(){
        return laporanUangMasukBulanan;
    }
    
    public String getLaporanUangKeluarHarian(){
        return laporanUangKeluarHarian;
    }
    
    public String getLaporanUangKeluarBulanan(){
        return laporanUangKeluarBulanan;
    }
    
    public String getUser(){
        return user;
    }
    
    public String getUangKeluar(){
        return uangKeluar;
    }
    
    public void setUsername(String username){
        this.username=username;
    }
    
    public void setNama(String nama){
        this.nama=nama;
    }
    
    public void setEmail(String email){
        this.email=email;
    }
    
    public String getUsername(){
        return username;
    }
    
    public String getNama(){
        return nama;
    }
    
    public String getEmail(){
        return email;
    }
    
    public void showAlert(AlertType type, String title, String header, String text){
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(text);
        alert.showAndWait();
    }
        
    public void harusAngka(TextField text){
        text.setOnKeyReleased(new EventHandler<javafx.scene.input.KeyEvent>() {
            @Override
            public void handle(javafx.scene.input.KeyEvent event) {
                if (!text.getText().matches("[0-9]*")){
                    showAlert(AlertType.WARNING, "Avertissement", null, "Seuls les chiffres peuvent être !!");
                    text.setText("");
                    text.requestFocus();
                }
            }
        });
        
    }
            
    public void animationFade(Node e){
        FadeTransition x = new FadeTransition(new Duration(1000), e);
        x.setFromValue(0);
        x.setToValue(100);
        x.setCycleCount(1);
        x.setInterpolator(Interpolator.LINEAR);
        x.play();
    }
    
}
