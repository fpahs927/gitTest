package test.teacher;

public interface Building {
    boolean checkAddress(String address);

    <T extends  Building> T toResult();
}
