import javax.xml.crypto.Data;
import java.sql.*;
import java.util.Scanner;

public class app {

    static Scanner scanner = new Scanner(System.in);

    static String yourChoise;

    long tr = 10;


    public static void main(String[] args){

        do{
            System.out.println("Lütfen yapmak istediğiniz işlemi seçiniz: \n 1-Öğrenci bilgilerini görme \n 2-Sınıf bilgilerini görme" +
                    " \n 3-Öğrenci ekleme \n 4-Sınıf ekleme \n 5-Öğrenci bilgilerini güncelleme \n 6-Sınıf bilgilerini güncelleme" +
                    " \n 7-Öğrenci silme \n 8-Sınıf silme \n 9-Çıkış");
            yourChoise = scanner.nextLine();

            switch (Integer.parseInt(yourChoise)){
                case 1:
                    getAllStudentsWithDetails();
                    break;
                case 2:
                    getAllClasses();
                    break;
                case 3:
                    addStudent();
                    break;
                case 4:
                    addClass();
                    break;
                case 5:
                    updateStudent();
                    break;
                case 6:
                    updateClass();
                    break;
                case 7:
                    deleteStudentById();
                    break;
                case 8:
                    deleteClasById();
                    break;
                case 9:
                    break;
                default:
                    System.out.println("lütfen doğru bir sayı giriniz.");

            }
        }while(Integer.parseInt(yourChoise)!=9);
        System.out.println("bizi tercih ettiğiniz için teşekkür ederiz \nPowered by Belet Programming");

    }

    public static void deleteClasById(){
        System.out.println("lütfen silinecek class'ın id'sini giriniz:");
        String inputClassId = scanner.nextLine();


        try{
            DatabaseConnection databaseConnection = DatabaseConnection.getInstance();
            Connection connection = databaseConnection.getConnection();
            String sql = "delete from classes where classId=?";

            PreparedStatement statement = connection.prepareStatement(sql);

            statement.setInt(1, Integer.parseInt(inputClassId));

            int rows = statement.executeUpdate();

            if(rows>0){
                System.out.println("sınıf başarıyla silindi.");
            }

            connection.close();

        }catch (Exception exception){
            exception.printStackTrace();
        }
    }

    public static void deleteStudentById(){
        System.out.println("lütfen silinecek öğrencinin id'sini giriniz:");
        String inputStudentId = scanner.nextLine();


        try{
            DatabaseConnection databaseConnection = DatabaseConnection.getInstance();
            Connection connection = databaseConnection.getConnection();
            String sql = "delete from students where studentId=?";

            PreparedStatement statement = connection.prepareStatement(sql);

            statement.setInt(1, Integer.parseInt(inputStudentId));

            int rows = statement.executeUpdate();

            if(rows>0){
                System.out.println("öğrenci başarıyla silindi.");
            }

            connection.close();
        }catch (Exception exception){
            exception.printStackTrace();
        }
    }

    public static void updateStudent(){
        System.out.println("lütfen güncellenecek öğrencinin id'sini giriniz:");
        String inputStudentId = scanner.nextLine();
        System.out.println("lütfen öğrencinin class Id'sini giriniz:");
        String inputClassId = scanner.nextLine();
        System.out.println("lütfen öğrenci adını giriniz:");
        String inputFirstName = scanner.nextLine();
        System.out.println("lütfen öğrencinin soyadını giriniz:");
        String inputLastName = scanner.nextLine();

        try{
            DatabaseConnection databaseConnection = DatabaseConnection.getInstance();
            Connection connection = databaseConnection.getConnection();
            String sql = "update students set ClassId=?, FirstName=?, LastName=? where studentId=?";

            PreparedStatement statement = connection.prepareStatement(sql);

            statement.setInt(1, Integer.parseInt(inputClassId));
            statement.setString(2,inputFirstName);
            statement.setString(3,inputLastName);
            statement.setInt(4, Integer.parseInt(inputStudentId));

            int rows = statement.executeUpdate();

            if(rows>0){
                System.out.println("öğrenci bilgileri başarıyla güncellendi.");
            }

            connection.close();
        }catch (Exception exception){
            exception.printStackTrace();
        }
    }

    public static void updateClass(){
        System.out.println("lütfen güncellenecek sınıfın id'sini giriniz:");
        String inputClassId = scanner.nextLine();
        System.out.println("lütfen sınıf adını güncelleyiniz:");
        String inputClassName = scanner.nextLine();

        try{
            DatabaseConnection databaseConnection = DatabaseConnection.getInstance();
            Connection connection = databaseConnection.getConnection();
            String sql = "update classes set className=? where classId=?";

            PreparedStatement statement = connection.prepareStatement(sql);

            statement.setString(1,inputClassName);
            statement.setInt(2, Integer.parseInt(inputClassId));

            int rows = statement.executeUpdate();

            if(rows>0){
                System.out.println("sınıf başarıyla güncellendi.");
            }

            connection.close();
        }catch (Exception exception){
            exception.printStackTrace();
        }
    }

    public static void addClass(){
        System.out.println("lütfen sınıf adı giriniz:");
        String inputClassName = scanner.nextLine();

        try{
            DatabaseConnection databaseConnection = DatabaseConnection.getInstance();
            Connection connection = databaseConnection.getConnection();

            String sql =  "insert into classes (className) values (?)";

            PreparedStatement statement = connection.prepareStatement(sql);

            statement.setString(1,inputClassName);

            int rows = statement.executeUpdate();

            if(rows>0){
                System.out.println("sınıf başarıyla eklendi.");
            }

            connection.close();
        }catch (Exception exception){
            exception.printStackTrace();
        }
    }

    public static void addStudent(){

        System.out.println("lütfen sınıf id'si giriniz:");
        String id = scanner.nextLine();
        System.out.println("lütfen öğrenci adını giriniz:");
        String inputName= scanner.nextLine();
        System.out.println("lütfen öğrenci soyadını giriniz:");
        String inputLastName= scanner.nextLine();




        try{

            DatabaseConnection databaseConnection = DatabaseConnection.getInstance();
            Connection connection = databaseConnection.getConnection();
            String sql = "insert into students (classid,firstname,lastname) values(?,?,?)";

            PreparedStatement statement = connection.prepareStatement(sql);

            statement.setInt(1, Integer.parseInt(id));
            statement.setString(2,inputName);
            statement.setString(3,inputLastName);

            int rows = statement.executeUpdate();

            if(rows>0){
                System.out.println("öğrenci başarıyla eklendi.");
            }


            connection.close();

        }catch (Exception exception){
            exception.printStackTrace();
        }
    }

    public static void getAllClasses(){
        try {

            DatabaseConnection databaseConnection = DatabaseConnection.getInstance();

            Connection connection = databaseConnection.getConnection();
            String sql = "Select * from classes";

            Statement statement = connection.createStatement();
            ResultSet result = statement.executeQuery(sql);

            while(result.next()){
                int classId = result.getInt("classId");
                String className = result.getString("className");

                System.out.println(classId + ": " + className);
            }

            connection.close();
        }catch (Exception exception){
            exception.printStackTrace();
        }
    }

    public static void getAllStudentsWithDetails(){
        try {


            DatabaseConnection databaseConnection = DatabaseConnection.getInstance();

            Connection connection = databaseConnection.getConnection();

            String sql = "Select students.studentId, students.firstName, students.LastName, classes.classname from students" +
                    " inner join classes on students.classId=classes.classId";

            Statement statement = connection.createStatement();
            ResultSet result = statement.executeQuery(sql);

            while(result.next()){
                int studentId = result.getInt("studentId");
                String firstName = result.getString("FirstName");
                String lastName = result.getString("LastName");
                String className = result.getString("className");

                System.out.println(studentId + ": " + firstName + ", " + lastName + ", " + className);
            }

            connection.close();
        }catch (Exception exception){
            exception.printStackTrace();
        }
    }

}
