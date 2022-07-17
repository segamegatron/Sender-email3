/**
 *
 Историческая справка

 Чтобы понять сложность разработки многопоточных приложений, нужно окунуться в историю. И так, на заре компьютерной эры, когда были изобретены микропроцессоры,
 разработчики писали последовательный код. Не уверен, что в то время кто-то вообще мог думать о параллельных вычислениях.
 Последовательная модель интуитивно понятна, команды выполняются на одном процессоре одна за другой. Скорость выполнения программ оставляла желать лучшего
 и для ее улучшения был выбран путь – увеличение количества транзисторов на интегральной схеме одного процессора. Возможно вы слышали Закон Мура –
 основателя компании Intel. Он предсказал удвоение количества транзисторов каждые два года. Мур оказался прав, с каждым годом процессоры становились все быстрее и быстрее
 . Написанные последовательные программы сами по себе начинали работать быстрее, без изменений в коде! Представляете, вы не пишите код, а ваша программа начинает
 работать все быстрее и быстрее год за годом. Фантастика! Ближе к 2000-м годам стало понятно, что экспоненциальный рост количества транзисторов заканчивается и скоро
 упрется в физические ограничения материалов, из которых их и делают. Следующее архитектурное решение повлияло на судьбы многих языков программирования, людей,
 систем и, в частности, стало возможным написание этой статьи. В замен одноядерных систем стали появляться многоядерные, которые и открыли дорогу многопоточному, параллельному
 программированию. С этого момента, для ускорение программ и вычислений, стало необходимым задействовать все доступные процессоры, а это возможно только при делении задачи
 на отдельные части и их параллельное выполнение на разных процессорах. На первый план вышли языки, исторически заточенные под многоядерную и распределенную работу.
 Одним из мастодонтов стал Earlang. Другим языкам, в том числе Java, пришлось адаптироваться и превращаться из последовательного языка в параллельный. Возможно поэтому
 многопоточность в Java является очень тяжелой темой для понимания и изучения. Свой отпечаток на написание параллельных программ оставляет наш последовательный образ
 мышления людей как биологического вида. Люди – однопоточные. Сделаю небольшую ремарку. В эпоху одноядерных процессоров так же было возможно “параллельное”
 выполнение за счет работы потоков операционной системы. Каждый процесс имеет свое адресное пространство и представляет собой выполняемую программу.
 Таких процессов может быть много, и в одноядерной системе, действительно, создается иллюзия параллельного выполнения, но на самом деле, процессор осуществляет
 выполнения только одного процесса в единицу времени. Для выполнения другого процесса, процессор осуществляет прерывания текущего процесса и запускает следующий.
 В глазах пользователя это происходит настолько быстро, и кажется, что программа работает параллельно, но на самом деле – она последовательна.
 Thread

 Начиная с версии Java 1.0 в пакете java.lang есть специальный класс, который позволяет создавать новые потоки (или треды) – Thread. Этот класс реализует интерфейс
 Runnable – специальный интерфейс, который является функциональным, начиная с версии Java 8, и содержит один метод, в котором должна быть описана задача потока – то
 что он будет делать:
 public abstract void run();
 1

 public abstract void run();

 Так как Thread – это класс, то и работа c ним осуществляется как и с другими классами. Чтобы создать поток, необходимо создать экземпляр класса с помощью конструктора.
 В классе Thread их целых 8 публичных! Чтобы запустить поток у экземпляра нужно вызвать метод start(). Здесь мы можем заметить некое противоречие: мы реализуем интерфейс
 Runnable, переопределяя метод run(). Почему мы вызываем вместо него start()? Дело в том, что вызвать метод run() у экземпляра Thread можно, но тогда код, который
 содержится в этом методе выполнится в текущем потоке, новый не будет создан. Вызывая метод start() мы гарантировано выполним код run() в новом потоке! Рассмотрим
 короткий пример: создадим три потока, но у одного вызовем метод run(), а у других start():
 public static void main(String[] args) {
 Runnable task = () -> System.out.println("Sit in Niva " + Thread.currentThread().getName());
 Thread t1 = new Thread(task);
 Thread t2 = new Thread(task);
 Thread t3 = new Thread(task);

 t1.run();
 t2.start();
 t3.start();
 }
 //Результат
 Sit in Niva main
 Sit in Niva Thread-1
 Sit in Niva Thread-2

 public static void main(String[] args) {
 Runnable task = () -> System.out.println("Sit in Niva " + Thread.currentThread().getName());
 Thread t1 = new Thread(task);
 Thread t2 = new Thread(task);
 Thread t3 = new Thread(task);

 t1.run();
 t2.start();
 t3.start();
 }
 //Результат
 Sit in Niva main
 Sit in Niva Thread-1
 Sit in Niva Thread-2

 Как вы видите, первый вызов осуществился в потоке main(), а остальные два каждый в своем потоке. Стоит отметить, что результат работы не является детерминированным,
 т.е. вы можете получить совсем другой вывод на консоль при повторном запуске, так как потоки выполняются одновременно.

 У каждого потока есть приоритет. Потоки с высоким приоритетом имеют преимущество над потоками с низким в глазах профилировщика потоков. Но это не значит, что потоки
 с низким приоритетом никогда не выполнятся. Технически завязываться на приоритет не всегда верно, т.к. на разных операционных системах реализованы разные механизмы
 параллельного выполнения потоков. Так же поток может быть помечен как демон. Это фоновый поток, который подходит для выполнения некоторых служебных задач. Целостность
 данных не гарантируется, т.к. все потоки-демоны прекращают свою работу, когда все другие потоки НЕ-демоны завершили свою работу. Поток, который завершил свою работу,
 нельзя запустить повторно.
 Способы создания потока

 В документации к классу Thread сказано, что создать новый поток можно двумя способами:

 Наследоваться от класса Thread
 Реализовать интерфейс Runnable

 //Наследование
 public class MyThread extends Thread {

@Override
public void run() {
System.out.println("Hello, I'm " + Thread.currentThread().getName());
}

public static void main(String[] args) {
new MyThread().start();
}
}

 //имплеминтация
 public class MyThread implements Runnable {

@Override
public void run() {
System.out.println("Hello, I'm " + Thread.currentThread().getName());
}

public static void main(String[] args) {
new Thread(new MyThread()).start();
}
}
 //Наследование
 public class MyThread extends Thread {

@Override
public void run() {
System.out.println("Hello, I'm " + Thread.currentThread().getName());
}

public static void main(String[] args) {
new MyThread().start();
}
}

 //имплеминтация
 public class MyThread implements Runnable {

@Override
public void run() {
System.out.println("Hello, I'm " + Thread.currentThread().getName());
}

public static void main(String[] args) {
new Thread(new MyThread()).start();
}
}


 Какой метод выбрать – решать вам. По существу при обоих подходах вы гарантировано должны переопределить метод run(). Однако в случае наследования вы можете дополнительно
 изменить и другие методы класса Thread, например, порядок обработки исключений. В общем рекомендация такая, если вам нужно переопределить метод run(), то реализуйте
 интерфейс Runnable. Если вы хотите задать дополнительное поведение потока, то используйте наследование.
 Методы класса Thread

 В классе Thread доступен ряд методов, позволяющих в разной степени манипулировать потоками исполнения, а так же синхронизировать их между собой.

 start() – синхронизированный метод. В результате выполнения метода JVM выполнит метод run() для данного потока и на выходе мы получим два запущенных одновременно
 потока – запущенный методом start() и поток, из которого был осуществлен запуск потока
 currentThread() – нативный метод, возвращает ссылку на текущий исполняющийся поток. Пример использования выше
 yield() – нативный метод. Его вызов указывает планировщику потоков на то, что текущий поток выполнил все важные операции и готов передать управление другому потоку
 с равным ему приоритетом. Вызов является лишь рекомендацией для планировщика, далеко не факт, что он прислушается к ней. yield() хорошо использовать во время отладки
 кода. С помощью него можно найти баги, связанные с гонкой потоков. Приведем пример, в котором два потока реализуют цикл от 0 до 10 и выводят в консоль каждый свое имя
 и номер текущий итерации. В конце каждого цикла сделаем вызов yield() и посмотрим на результаты.

 public static void main(String[] args) {
 Runnable task = () -> {
 for (int i = 0; i < 10; i++) {
 System.out.println(Thread.currentThread().getName() + " count = " + i);
 Thread.yield();
 }
 };
 Thread thread1 = new Thread(task);
 Thread thread2 = new Thread(task);
 thread1.start();
 thread2.start();
 }

 //Вывод
 Thread-0 count = 0
 Thread-1 count = 0
 Thread-0 count = 1
 Thread-1 count = 1
 Thread-0 count = 2
 Thread-1 count = 2
 Thread-0 count = 3
 Thread-1 count = 3
 Thread-0 count = 4
 Thread-1 count = 4
 Thread-0 count = 5
 Thread-1 count = 5
 Thread-0 count = 6
 Thread-1 count = 6
 Thread-0 count = 7
 Thread-1 count = 7
 Thread-0 count = 8
 Thread-1 count = 8
 Thread-0 count = 9
 Thread-1 count = 9


 public static void main(String[] args) {
 Runnable task = () -> {
 for (int i = 0; i < 10; i++) {
 System.out.println(Thread.currentThread().getName() + " count = " + i);
 Thread.yield();
 }
 };
 Thread thread1 = new Thread(task);
 Thread thread2 = new Thread(task);
 thread1.start();
 thread2.start();
 }

 //Вывод
 Thread-0 count = 0
 Thread-1 count = 0
 Thread-0 count = 1
 Thread-1 count = 1
 Thread-0 count = 2
 Thread-1 count = 2
 Thread-0 count = 3
 Thread-1 count = 3
 Thread-0 count = 4
 Thread-1 count = 4
 Thread-0 count = 5
 Thread-1 count = 5
 Thread-0 count = 6
 Thread-1 count = 6
 Thread-0 count = 7
 Thread-1 count = 7
 Thread-0 count = 8
 Thread-1 count = 8
 Thread-0 count = 9
 Thread-1 count = 9

 В примере выше мы видим синхронную работу потоков, но если запустить этот код несколько раз, или увеличить количество итераций с 10 до 500, то можно увидеть,
 что время от времени планировщик потоков игнорирует рекомендацию yield() и вывод может быть примерно таким:
 Thread-0 count = 0
 Thread-0 count = 1
 Thread-0 count = 2
 Thread-1 count = 0
 Thread-0 count = 3
 Thread-1 count = 1
 Thread-0 count = 4
 Thread-1 count = 2
 Thread-0 count = 5
 Thread-1 count = 3
 Thread-0 count = 6
 Thread-1 count = 4
 Thread-0 count = 7
 Thread-1 count = 5
 Thread-0 count = 8
 Thread-1 count = 6
 Thread-1 count = 7
 Thread-1 count = 8
 Thread-0 count = 9
 Thread-1 count = 9

 Thread-0 count = 0
 Thread-0 count = 1
 Thread-0 count = 2
 Thread-1 count = 0
 Thread-0 count = 3
 Thread-1 count = 1
 Thread-0 count = 4
 Thread-1 count = 2
 Thread-0 count = 5
 Thread-1 count = 3
 Thread-0 count = 6
 Thread-1 count = 4
 Thread-0 count = 7
 Thread-1 count = 5
 Thread-0 count = 8
 Thread-1 count = 6
 Thread-1 count = 7
 Thread-1 count = 8
 Thread-0 count = 9
 Thread-1 count = 9

 sleep(long millis) throws InterruptedException – нативный метод. Приостанавливает выполнение текущего потока на количество миллисекунд, переданных в параметре.
 При этом поток не теряет свои мониторы. Выбрасывает исключение InterruptedException в случае, если любой другой поток останавливает спящий. Существует вторая
 форма этого метода с двумя параметрами, sleep(long millis, int nanos). Второй параметр позволяет указывать более точное время остановки в наносекундах. Работает
 только в средах, которые это поддерживают. Следующий пример показывает работу метода, выводит на экран счетчик от 0 до 9 с задержкой в 1 секунду

 public static void main(String[] args) {
 Runnable task = () -> {
 for (int i = 0; i < 10; i++) {
 System.out.println(Thread.currentThread().getName() + " count = " + i);
 try {
 Thread.sleep(1000);
 } catch (InterruptedException e) {
 e.printStackTrace();
 }
 }
 };
 Thread thread1 = new Thread(task);
 thread1.start();
 }

 public static void main(String[] args) {
 Runnable task = () -> {
 for (int i = 0; i < 10; i++) {
 System.out.println(Thread.currentThread().getName() + " count = " + i);
 try {
 Thread.sleep(1000);
 } catch (InterruptedException e) {
 e.printStackTrace();
 }
 }
 };
 Thread thread1 = new Thread(task);
 thread1.start();
 }

 stop() – является нерекомендованным к использованию. Его вызов уничтожает поток. Его использование небезопасно т.к. во время смерти потока, все его мониторы
 освобождаются, занятые ресурсы могут быть в неконсистентном состоянии, а т.к. монитор свободен, то другие потоки могут воспользоваться этими данными.
 Это вообще не гуд. Следующий пример показывает, как в синхронизированном блоке осуществляется операция инткремента одним потоком, а спустя 5 секунд у
 него вызывается метод stop() и монитором завладевает другой поток, продолжая операцию инкремента. При запуске кода ниже вы увидите сначала в консоли
 первый поток, а спустя 5 секунд второй

 public class ThreadExamples {
 static long number = 0;
 static Object lock = new Object();

 public static void main(String[] args) throws InterruptedException {
 Runnable task = () -> {
 synchronized (lock) {
 System.out.println(Thread.currentThread());
 while (true) {
 number++;
 }
 }
 };
 Thread thread1 = new Thread(task);
 thread1.start();
 new Thread(task).start();
 Thread.sleep(5000);
 thread1.stop();
 }
 }

 //Вывод
 Thread[Thread-0,5,main]
 //спустя 5 секунд
 Thread[Thread-1,5,main]


 public class ThreadExamples {
 static long number = 0;
 static Object lock = new Object();

 public static void main(String[] args) throws InterruptedException {
 Runnable task = () -> {
 synchronized (lock) {
 System.out.println(Thread.currentThread());
 while (true) {
 number++;
 }
 }
 };
 Thread thread1 = new Thread(task);
 thread1.start();
 new Thread(task).start();
 Thread.sleep(5000);
 thread1.stop();
 }
 }

 //Вывод
 Thread[Thread-0,5,main]
 //спустя 5 секунд
 Thread[Thread-1,5,main]

 Проблема заключается в том, что в синхронизированном блоке может осуществляться не атомарная операция, а при вызове stop() монитор освобождается мгновенно, поэтому ресурс может быть не в правильном состоянии. Инкремент как раз относится к таким. Если взглянуть на его байт-код, то видно, что операция проходит в три этапа – получение значения, увеличение на единицу, запись значения. И между любыми из этих инструкций может произойти прерывание потока.
 GETSTATIC thread/ThreadExamples.number : J
 LCONST_1
 LADD
 PUTSTATIC thread/ThreadExamples.number : J
 1
 2
 3
 4

 GETSTATIC thread/ThreadExamples.number : J
 LCONST_1
 LADD
 PUTSTATIC thread/ThreadExamples.number : J

 interrupt() – прерывает поток. В отличие от stop() делает это через установку флага прерывания. Если этот метод вызывается у потока, который находится в состоянии ожидания, то ему передается исключение InterruptedException. Когда поток блокирован на операциях ввода-вывода, то сначала закроется канал передачи, получив исключение InterruptibleChannel, а затем будет установлен статус прерывания, а поток получит исключение ClosedByInterruptException. Если в предыдущем примере заменить stop() на interrupt(), то как только выполнится эта команда, вы не увидите второй строчки, которая говорит о захвате монитора вторых потоком. Все дело в том, что метод interrupt() устанавливает флаг прерывания и с помощью него и метода interrupted() мы можем корректно завершить выполнение потока. Для этого нужно в предыдущем примере в условии while вместо true добавить !Thread.currentThread().isInterrupted() или !Thread.interrupted(). Разница между этими вызовами описана ниже

 public class ThreadExamples {
 static long number = 0;
 static Object lock = new Object();

 public static void main(String[] args) throws InterruptedException {
 Runnable task = () -> {
 synchronized (lock) {
 System.out.println(Thread.currentThread());
 while (!Thread.currentThread().isInterrupted()) {
 number++;
 }
 }
 };
 Thread thread1 = new Thread(task);
 thread1.start();
 new Thread(task).start();
 Thread.sleep(5000);
 thread1.interrupt();
 }
 }
 1
 2
 3
 4
 5
 6
 7
 8
 9
 10
 11
 12
 13
 14
 15
 16
 17
 18
 19
 20

 public class ThreadExamples {
 static long number = 0;
 static Object lock = new Object();

 public static void main(String[] args) throws InterruptedException {
 Runnable task = () -> {
 synchronized (lock) {
 System.out.println(Thread.currentThread());
 while (!Thread.currentThread().isInterrupted()) {
 number++;
 }
 }
 };
 Thread thread1 = new Thread(task);
 thread1.start();
 new Thread(task).start();
 Thread.sleep(5000);
 thread1.interrupt();
 }
 }

 interrupted() – статический метод. Возвращает статус прерывания текущего потока. Причем значение true вернется только один раз для прерванного потока! Вызов этого метода очищает статус потока. Если вы хотите узнать статус прерывания без очистки статуса, то нужно воспользоваться нестатическим методом isInterrupted(). Оба метода внутри себя вызывают нативный метод isInterrupted(boolean ClearInterrupted)
 destroy() – вызов этого метода выбросит исключение NoSuchMethodError. Изначально он задумывался для уничтожения потока без выполнения любого вида очистки. Как вы понимаете такой подход просто невозможем в современном мире, ведь поток может блокировать важные ресурсы или работать с критическими секциями, или ненадежными соединениями. В версиях JDK выше 8 данного метода больше нет
 isAlive() – нативный метод. Возвращает true если текущий поток запущен, и еще не мертв.
 suspend() и resume(). Оба метода помечены как @Deprecated. Первый приостанавливает выполнения потока, второй его продолжает. Казалось бы, в чем проблема? А она в следующем. Приостанавливая поток методом suspend(), он сохраняет блокировки и все мониторы на, возможно, критически важные ресурсы. Пока данный поток не будет возобновлен методом resume(), другие потоки не смогут получить доступ к ресурсам. Так же во время возобновления работы может случится deadlock, если поток, желающий возобновить приостановленный поток попытается блокировать монитор перед вызовом resume(). Стоит отметить, что приостанавливая поток, его состояние остается как RUNNABLE (о состояниях потока мы поговорим ниже), но фактически поток ничего не делает. Продемонстрируем на том же примере с инкрементом. Следующая цепочка вызовов показывает, что в течении 5 секунд приостановки потока инкриментируемая переменная не изменяется, при этом поток – RUNNABLE, а после вызова resume(), спустя 1 секунду, мы видим другое число

 public class ThreadExamples {
 static long number = 0;

 public static void main(String[] args) throws InterruptedException {
 Runnable task = () -> {
 while (true) {
 number++;
 }
 };

 Thread thread1 = new Thread(task);
 thread1.start();
 Thread.sleep(1000);
 thread1.suspend();
 System.out.println(number);
 Thread.sleep(5000);
 System.out.println(number);
 System.out.println(thread1.getState());
 thread1.resume();
 Thread.sleep(1000);
 System.out.println(number);
 }
 }

 //Вывод
 461493368
 461493368
 RUNNABLE
 998923889
 1
 2
 3
 4
 5
 6
 7
 8
 9
 10
 11
 12
 13
 14
 15
 16
 17
 18
 19
 20
 21
 22
 23
 24
 25
 26
 27
 28
 29

 public class ThreadExamples {
 static long number = 0;

 public static void main(String[] args) throws InterruptedException {
 Runnable task = () -> {
 while (true) {
 number++;
 }
 };

 Thread thread1 = new Thread(task);
 thread1.start();
 Thread.sleep(1000);
 thread1.suspend();
 System.out.println(number);
 Thread.sleep(5000);
 System.out.println(number);
 System.out.println(thread1.getState());
 thread1.resume();
 Thread.sleep(1000);
 System.out.println(number);
 }
 }

 //Вывод
 461493368
 461493368
 RUNNABLE
 998923889

 setPriority(int newPriority) – публичный final метод, который устанавливает приоритет для потока. При создании потока его приоритет равен 5. В метод можно передать значение аргумента от 1 (минимальный приоритет) до 10 (максимальный приоритет). Передача любого числа не из данного диапазона приведет к исключению IllegalArgumentException. Соответственно, метод getPriority() вернет текущий приоритет потока.
 setName(String name) – устанавливает имя потока. Обратите внимание на то, что этот метод синхронизирован. Так же параметром нельзя передавать null, иначе будет получено исключение NullPointerException. Соответственно, метод getName() вернет вам имя потока.
 getThreadGroup() – вернет вам объект типа ThreadGroup, или null, если текущей поток уже был остановлен. Каждый поток относится к определенной группе потоков, при этом одна группа может быть включена в другую. Потоки внутри группы имеют доступ к информации только своей группы.
 activeCount() – статический метод возвращает оценочное число активных потоков в группе, к которой относится поток. Число является лишь оценкой, т.к. количество активных потоков может изменится во время вызова.
 enumerate(Thread tarray[]) – статический метод. Он помещает в массив tarray[] все активные потоки группы и всех подгрупп данного потока. Оценить размер массива можно методом activeCount(). Если размер массива окажется меньше количества активных потоков, то после заполнения массива, остальные потоки будут проигнорированы. Этот метод заключает в себе гонку потоков, поэтому его лучше использовать только при отладки.
 countStackFrames() – нативный метод, помеченный как @Deprecated, из-за его связи с методом suspend(). Он возвращает количество фреймов в стеке в данном потоке. При этом поток должен быть остановлен методом suspend().
 join(long millis), join(long millis, int nanos) и join() – последние два метода вызывают внутри себя первый, поэтому расскажем как работает первый. Он ожидает завершение потока определенное количество миллисекунд. По сути этот метод вызывает внутри себя wait() класса Object в цикле while(isAlive()). При вызове join() т.е. когда количество миллисекунд равно 0, ожидание завершения потока будет до тех пор, пока он не будет прерван. Если передать количество миллисекунд, то ожидание будет длиться только выделенное время. Рекомендуется использовать join() вместо wait(), потому что join() можно вызывать на конкретном экземпляре класса Thread. В следующем примере главный поток ожидает завершения 2-х потоков, затем продолжает свое выполнение

 public static void main(String[] args) throws InterruptedException {
 Runnable task = () -> {
 for (int i = 0; i < 5; i++) {
 System.out.println(Thread.currentThread().getName() + " " + i);
 }
 };
 Thread thread1 = new Thread(task);
 Thread thread2 = new Thread(task);
 thread1.start();
 thread2.start();

 thread1.join();
 System.out.println(thread1.getName() + " завершил работу");
 thread2.join();
 System.out.println(thread2.getName() + " завершил работу");

 }

 //Вывод
 Thread-1 0
 Thread-1 1
 Thread-0 0
 Thread-1 2
 Thread-0 1
 Thread-1 3
 Thread-0 2
 Thread-0 3
 Thread-1 4
 Thread-0 4
 Thread-0 завершил работу
 Thread-1 завершил работу
 1
 2
 3
 4
 5
 6
 7
 8
 9
 10
 11
 12
 13
 14
 15
 16
 17
 18
 19
 20
 21
 22
 23
 24
 25
 26
 27
 28
 29
 30
 31

 public static void main(String[] args) throws InterruptedException {
 Runnable task = () -> {
 for (int i = 0; i < 5; i++) {
 System.out.println(Thread.currentThread().getName() + " " + i);
 }
 };
 Thread thread1 = new Thread(task);
 Thread thread2 = new Thread(task);
 thread1.start();
 thread2.start();

 thread1.join();
 System.out.println(thread1.getName() + " завершил работу");
 thread2.join();
 System.out.println(thread2.getName() + " завершил работу");

 }

 //Вывод
 Thread-1 0
 Thread-1 1
 Thread-0 0
 Thread-1 2
 Thread-0 1
 Thread-1 3
 Thread-0 2
 Thread-0 3
 Thread-1 4
 Thread-0 4
 Thread-0 завершил работу
 Thread-1 завершил работу

 dumpStack() – статический метод, возвращает стек вызовов текущего потока в стандартный поток ошибок. Метод используется только при отладки.
 setDaemon(boolean on) – делает поток-демоном. Необходимо вызывать метод до старта потока, иначе будет получено исключение IllegalThreadStateException. В следующем примере мы видим, что поток-демон прекратит выводить в консоль Hello, I’m a daemon спустя 1 миллисекунду после завершения единственного потока НЕ-демона

 public static void main(String[] args) throws InterruptedException {
 Runnable task = () -> {
 while (true){
 System.out.println("Hello, I'm a daemon");
 }
 };

 Thread thread = new Thread(task);
 thread.setDaemon(true);
 thread.start();
 Thread.sleep(1);

 }

 //Вывод
 Hello, I'm a daemon
 Hello, I'm a daemon
 ...
 1
 2
 3
 4
 5
 6
 7
 8
 9
 10
 11
 12
 13
 14
 15
 16
 17
 18

 public static void main(String[] args) throws InterruptedException {
 Runnable task = () -> {
 while (true){
 System.out.println("Hello, I'm a daemon");
 }
 };

 Thread thread = new Thread(task);
 thread.setDaemon(true);
 thread.start();
 Thread.sleep(1);

 }

 //Вывод
 Hello, I'm a daemon
 Hello, I'm a daemon
 ...

 isDaemon() – возвращает статус, является ли данный поток демоном.
 checkAccess() – определяет есть ли права текущего запущенного потока на изменения данного потока. Выбрасывает исключение SecurityException, если таких прав нет.
 getContextClassLoader() – возвращает контекст загрузчика классов для этого потока. Контекст создается потоком при загрузке из потока классов и ресурсов. По умолчанию передается контекст родительского потока. Установить контекст для потока можно вызвав метод setContextClassLoader(ClassLoader cl).
 holdsLock(Object obj) – нативный метод, возвращает true тогда и только тогда, когда текущий поток блокирует монитор переданного как аргумент объекта. В примере мы обращаемся к методу не через экземпляр класса Thread, а как к статическому Thread.holdsLock(lock) и делаем два вызова – один внутри синхронизированного блока в новом потоке, второй в главном потоке. Семантически вызов выглядит одинаково, но результаты будут разные. В случае вызова внутри блока synchronized мы получим true, а в главном потоке – false, даже несмотря на то, что мы обращаемся к статическому методу.

 public class ThreadExamples {
 static long number = 0;
 static Object lock = new Object();

 public static void main(String[] args) {
 Runnable task = () -> {
 synchronized (lock) {
 System.out.println(Thread.currentThread().getName() + " " + Thread.holdsLock(lock));
 while (!Thread.currentThread().isInterrupted()) {
 number++;
 }
 }
 };
 Thread thread = new Thread(task);
 thread.start();

 System.out.println(Thread.currentThread().getName() + " " + Thread.holdsLock(lock));

 }
 }
 1
 2
 3
 4
 5
 6
 7
 8
 9
 10
 11
 12
 13
 14
 15
 16
 17
 18
 19
 20

 public class ThreadExamples {
 static long number = 0;
 static Object lock = new Object();

 public static void main(String[] args) {
 Runnable task = () -> {
 synchronized (lock) {
 System.out.println(Thread.currentThread().getName() + " " + Thread.holdsLock(lock));
 while (!Thread.currentThread().isInterrupted()) {
 number++;
 }
 }
 };
 Thread thread = new Thread(task);
 thread.start();

 System.out.println(Thread.currentThread().getName() + " " + Thread.holdsLock(lock));

 }
 }

 getStackTrace() – возвращает массив элементов StackTraceElement текущего потока.
 getAllStackTraces() – статический метод, возвращает Map всех StackTraceElement для каждого живого потока. Нужно учитывать, что результирующая Map не является детерминированной, каждый новый вызов метода будет возвращать разные результаты.
 onSpinWait() – метод появился в Java 9 и является кандидатом на добавление в реализацию HotSpot JVM. Вызов команды является своего рода разновидностью yield(), эквивалент команде PAUSE процессоров x86. Вызывая его вы указываете процессору, что поток работает в бесконечном цикле, ожидания наступления некоторых событий и процессор, ориентируясь на эту информацию, может оптимизировать работу этого бесконечного цикла.

 Мы перечислили основные публичные методы класса Thread. Дополнительно стоит рассмотреть некоторые методы из класса Object, тесно связанные с Thread. Это методы notify(), notifyAll(), wait(long timeout), wait(long timeout, int nanos) и wait().

 wait(long timeout), wait(long timeout, int nanos) и wait() – как написано выше, этот перегруженный метод вызывается из метода join(). Он нативный, вынуждает вызывающий поток исполнения уступить монитор объекта и перейти в состояние ожидания до тех пор, пока другой поток не войдет в этот монитор и не вызовет метод notify().
 notify() – нативный метод, возобновляет исполнение потока, из которого был вызван wait() для того же самого объекта.
 notifyAll() – нативный метод, возобновляет все потоки из которых был вызван метод wait() для того же самого объекта. Одному из всех потоков будет предоставлен доступ к объекту. Какому именно, решит планировщик потоков.

 Состояние потоков

 Внутри класса Thread определено перечисление State, в котором заданы все возможные состояния потока. В каждый момент времени у потока может быть только одно состояние. Далее список всех состояний:

 NEW – поток создан, но еще не запущен
 RUNNABLE – поток запущен и выполняется в JVM
 BLOCKED – поток приостановлен, ожидает получения блокировки
 WAITING – поток приостановлен и ожидает вызова некоторого действия
 TIMED_WAITING – поток приостановлен, ожидает некоторого действия или будет возобновлен по окончании времени ожидания
 TERMINATED – поток завершил выполнение

 Заключение

 В данной статье мы рассмотрели лишь малую часть работы с классом Thread. Познакомились с историей многопоточности. В следующих частях мы поговорим о работе с исключениями класса Thread, и начнем рассмотрение класса Executor.

 */