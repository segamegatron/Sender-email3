package ru.sgrc.datasender;



class Test2 {
    Integer age =29;
}



public class Test extends Test2{
    String name = "Test";

    public static void main(String[] args) throws Throwable {
        Test test = new Test();
        test.finalize();


        Test2 test2 = new Test2();
        System.out.println("Age " + test2.age);
        test2.age = 33;
        System.out.println("Age " + test2.age);


//        Test ctest = (Test) new Test2();

//        System.out.println(ctest.name);

    }
}
