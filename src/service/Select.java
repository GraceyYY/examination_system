package service;

import tools.Database;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Select {

    public static void getAllStudents(Connection connection) {

        try (Statement st = Database.getStatement(connection);
             ResultSet rs = Database.executeSQL(st, "SELECT * FROM students")) {
            while (rs.next()) {
                int id = rs.getInt("student_id");
                String name = rs.getString("name");
                int age = rs.getInt("age");
                String gender = rs.getString("gender");
                System.out.println("学号：" + id + "，姓名：" + name + "，年龄：" + age + "，性别：" + gender);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public static void getStudentInfoByName(Connection connection, String name) {
        try (Statement st = Database.getStatement(connection);
             ResultSet rs = Database.executeSQL(st, "SELECT * FROM students WHERE name = \"" + name + "\"")) {
            while (rs.next()) {
                int id = rs.getInt("student_id");
                int age = rs.getInt("age");
                String gender = rs.getString("gender");
                System.out.println("学号：" + id + "，姓名：" + name + "，年龄：" + age + "，性别：" + gender);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    public static void getScoreByStudentName(Connection connection, String name) {
        String scores = "";
        try (Statement st = Database.getStatement(connection);
             ResultSet rs = Database.executeSQL(st, "SELECT student_id, subjects.name AS subject_name, score FROM scores INNER JOIN subjects USING (subject_id) WHERE student_id=(SELECT student_id FROM students WHERE name = \"" + name + "\")")) {
            while (rs.next()) {
                String subjectName = rs.getString("subject_name");
                int score = rs.getInt("score");
                scores += ("(" + subjectName + ": " + score + ")");
            }
            System.out.println(name + "的成绩：" + scores);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static void getScoreByStudentId(Connection connection, int id) {
        String scores = "";
        try (Statement st = Database.getStatement(connection);
             ResultSet rs = Database.executeSQL(st, "SELECT name FROM students WHERE student_id = " + id)) {
            while (rs.next()) {
                getScoreByStudentName(connection, rs.getString("name"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void getScoreByTeacher(Connection connection, int teacherId) {
        String students = "";
        try (Statement st = Database.getStatement(connection);
             ResultSet rs = Database.executeSQL(st, "SELECT student_id FROM teacher_subject INNER JOIN scores USING(subject_id) WHERE teacher_id=" + teacherId)) {
            while (rs.next()) {
                int studentId = rs.getInt("student_id");
                getScoreByStudentId(connection, studentId);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void getScoreBySubject(Connection connection, int subjectId) {
        String subjectName = "";
        try (Statement st = Database.getStatement(connection);
             ResultSet rs = Database.executeSQL(st, "SELECT name FROM subjects WHERE subject_id = " + subjectId)) {
            while (rs.next()) {
                subjectName = rs.getString("name");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        String scores = "";
        try (Statement st = Database.getStatement(connection);
             ResultSet rs = Database.executeSQL(st, "SELECT name, score FROM scores INNER JOIN students USING (student_id) WHERE subject_id=" + subjectId)) {
            while (rs.next()) {
                String studentName = rs.getString("name");
                int score = rs.getInt("score");
                scores += (studentName + "的成绩：" + "(" + subjectName + ": " + score + ")\n");
            }
            System.out.println(scores);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void getAllTeachersInfo(Connection connection) {
        try (Statement st = Database.getStatement(connection);
             ResultSet rs = Database.executeSQL(st, "SELECT * FROM teachers")) {
            while (rs.next()) {
                int id = rs.getInt("teacher_id");
                String name = rs.getString("name");
                String gender = rs.getNString("gender");
                int age = rs.getInt("age");
                System.out.println("教师编号：" + id + "，姓名：" + name + "，年龄：" + age + "，性别：" + gender);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void getTeacherByName(Connection connection, String name) {
        try (Statement st = Database.getStatement(connection);
             ResultSet rs = Database.executeSQL(st, "SELECT * FROM teachers WHERE name = \"" + name + "\"")) {
            while (rs.next()) {
                int id = rs.getInt("teacher_id");
                String gender = rs.getNString("gender");
                int age = rs.getInt("age");
                System.out.println("教师编号：" + id + "，姓名：" + name + "，年龄：" + age + "，性别：" + gender);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void getAllSubjectsInfo(Connection connection) {
        try (Statement st = Database.getStatement(connection);
             ResultSet rs = Database.executeSQL(st, "SELECT * FROM subjects")) {
            while (rs.next()) {
                int id = rs.getInt("subject_id");
                String name = rs.getString("name");
                String exam = rs.getNString("exam");
                int total = rs.getInt("total_score");
                System.out.println("课程编号：" + id + "，课程名称：" + name + "，考试信息：" + exam + "，总分：" + total);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void getSubjectByName(Connection connection, String name) {
        try (Statement st = Database.getStatement(connection);
             ResultSet rs = Database.executeSQL(st, "SELECT * FROM subjects WHERE name =\"" + name + "\"")) {
            while (rs.next()) {
                int id = rs.getInt("subject_id");
                String exam = rs.getNString("exam");
                int total = rs.getInt("total_score");
                System.out.println("课程编号：" + id + "，课程名称：" + name + "，考试信息：" + exam + "，总分：" + total);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void getSubjectByTeacher(Connection connection, int teacherId) {
        try (Statement st = Database.getStatement(connection);
             ResultSet rs = Database.executeSQL(st, "SELECT * FROM teacher_subject INNER JOIN subjects USING(subject_id) WHERE teacher_id=" + teacherId)) {
            while (rs.next()) {
                int id = rs.getInt("subject_id");
                String name = rs.getString("name");
                String exam = rs.getNString("exam");
                int total = rs.getInt("total_score");
                System.out.println("课程编号：" + id + "，课程名称：" + name + "，考试信息：" + exam + "，总分：" + total);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


}
