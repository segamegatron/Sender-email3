package ru.sgrc.datasender;

public class ThreadComent {

    /**
     * "" "Эта статья является учебной запиской моей первой книги по изучению многопоточности Java и высокому уровню параллелизма,
     *  Заголовок «<Базовая технология многопоточного программирования Java >>», и автор является старшим менеджером проекта крупного бизнеса.
     *  Старший Гао Хунянь отдает ему дань здесь. Я буду сотрудничать с документацией по разработке, а также с этой книгой и другими блогами.
     *  Выделенные статьи для изучения, в то же время делаю несколько простых резюме. У меня нет некоторых основных вещей
     *  После детального анализа рекомендуется, чтобы вы подвергались многопоточности на других языках или не имели системы для изучения слишком большого количества потоков
     *  Разработчиков. Следует также отметить, что некоторые документы на английском языке, представленные в блоге, просты
     *  Переведено, важная часть будет переведена подробно, это не будет тратить время, это то, что я хочу улучшить
     *  Ваш собственный уровень чтения английского и способность просматривать документы, те, кто хочет накапливать внутреннюю силу, могут использовать Google
     *  Переведите себя, чтобы внимательно прочитать документ. (Китайские документы рекомендуются только для справки, ведь вы знаете ...)
     *  Подробный код см .: https://github.com/youaresherlock/multithreadingforjavanotes
     * Why are Thread.stop, Thread.suspend and Thread.resume
     *  Устарел английский перевод документов
     *  Перечитайте причину, по которой методы stop (), suspend (), resume () в классе Thread устарели при чтении документа.
     *  Моя версия JDK 9.0.4, поэтому переведенная статья дается 9, содержание статьи на самом деле не сильно отличается
     *  Адреса java9 и java11 следующие:
     * https://docs.oracle.com/javase/9/docs/api/java/lang/doc-files/threadPrimitiveDeprecation.html
     * https://docs.oracle.com/en/java/javase/11/docs/api/java.base/java/lang/doc-files/threadPrimitiveDeprecation.html
     *  Английский и китайский следующие:
     * Java Thread Primitive Deprecation
     * Why is Thread.stop deprecated?
     * Because it is inherently unsafe. Stopping a thread causes it to unlock all the monitors
     * that it has locked. (The monitors are unlocked as the ThreadDeath exception propagates
     * up the stack.) If any of the objects previously protected by these monitors were in an
     * inconsistent state, other threads may now view these objects in an inconsistent state.
     * Such objects are said to be damaged. When threads operate on damaged objects, arbitrary
     * behavior can result. This behavior may be subtle and difficult to detect, or it may be
     * pronounced. Unlike other unchecked exceptions, ThreadDeath kills threads silently; thus,
     * the user has no warning that his program may be corrupted. The corruption can manifest
     * itself at any time after the actual damage occurs, even hours or days in the future.
     *  Java поток класс Поток оригинальный устаревший
     *  Почему метод Thread.stop устарел?
     *  Потому что это небезопасно. Остановка потока приводит к освобождению всех заблокированных мониторов. (Когда ThreadDeath
     *  Когда исключение распространяется в стеке, монитор будет разблокирован. ) Если объекты, ранее защищенные этими мониторами, находятся в несогласованном состоянии,
     *  Тогда другие потоки могут теперь просматривать эти объекты в несогласованном состоянии. Эти объекты уничтожены. Когда нить
     *  При работе на разрушенном объекте произойдет произвольное поведение. Такое поведение может быть тонким и трудным для обнаружения, или очень очевидным
     *  Значительно. В отличие от других непроверенных исключений, исключение ThreadDeath молча уничтожает поток, поэтому пользователь
     *  Последовательность может быть уничтожена без предупреждения. После фактического уничтожения явление уничтожения программы может произойти в любое время,
     *  Даже в ближайшие несколько часов или дней.
     * Couldn't I just catch the ThreadDeath exception and fix the damaged object?
     * In theory, perhaps, but it would vastly complicate the task of writing correct
     * multithreaded code. The task would be nearly insurmountable for two reasons:
     * 	1.A thread can throw a ThreadDeath exception almost anywhere. All synchronized
     * methods and blocks would have to be studied in great detail, with this in mind.
     * 	2.A thread can throw a second ThreadDeath exception while cleaning up from the
     * first (in the catch or finally clause). Cleanup would have to be repeated till
     * it succeeded. The code to ensure this would be quite complex.
     * In sum, it just isn't practical.
     *  Не могу ли я поймать исключение ThreadDeath и восстановить поврежденный объект?
     *  Теоретически, это возможно, но очень сложно написать правильный многопоточный код. Эта задача почти непреодолима,
     *  Есть две причины:
     * 	 1. Поток может генерировать исключение ThreadDeath практически в любом месте. Все синхронизированные методы и блоки синхронизированных операторов
     *  Это должно быть изучено очень подробно, принимая это во внимание.
     * 	 2. Когда первый элемент, расположенный в предложении catch или finally, очищает поток, может возникнуть второе исключение ThreadDeath.
     *  Очистку нужно будет повторять до тех пор, пока она не будет успешной. Код для обеспечения этого достаточно сложен.
     *  В общем, такой подход нереален.
     * What about Thread.stop(Throwable)?
     * In addition to all of the problems noted above, this method may be used to generate
     * exceptions that its target thread is unprepared to handle (including checked
     * exceptions that the thread could not possibly throw, were it not for this method).
     * For example, the following method is behaviorally identical to Java's throw operation,
     * but circumvents the compiler's attempts to guarantee that the calling method has
     * declared all of the checked exceptions that it may throw:
     * static void sneakyThrow(Throwable t) {
     *     Thread.currentThread().stop(t);
     * }
     *  А как насчет Thread.stop (Throwable obj)?
     *  В дополнение ко всем проблемам, упомянутым выше, этот метод также можно использовать для генерации исключений, целевой поток которых не готов к обработке. (Если нет
     *  Этот метод, включая проверку исключения, которое текущий поток не может выбросить)
     *  Например, следующий метод ведет себя так же, как операция trow Java, но избегает попыток компилятора гарантировать вызов
     *  Метод объявляет все исключения проверки, которые он может бросить
     * What should I use instead of Thread.stop?
     * Most uses of stop should be replaced by code that simply modifies some variable
     * to indicate that the target thread should stop running. The target thread should
     * check this variable regularly, and return from its run method in an orderly fashion
     * if the variable indicates that it is to stop running. To ensure prompt communication
     * of the stop-request, the variable must be volatile (or access to the variable
     * must be synchronized).
     * For example, suppose your applet contains the following start, stop and run methods:
     *  Что я должен использовать вместо метода Thread.stop ()?
     *  Большинство остановок использования следует заменить, просто изменив некоторые переменные, чтобы указать, что целевой поток должен прекратить выполнение кода. глаз
     *  Целевой поток должен периодически проверять эту переменную. Если переменная указывает, что она остановится, она должна запускаться из целевого потока упорядоченным образом.
     *  Метод возвращает. Чтобы обеспечить своевременную передачу запроса на останов, переменная должна быть изменчивой (или доступ к переменной должен быть синхронизирован)
     *  Например, предположим, что ваше приложение содержит следующие методы запуска, остановки и запуска.
     * private Thread blinker;
     * public void start() {
     *     blinker = new Thread(this);
     *     blinker.start();
     * }
     * public void stop() {
     *          blinker.stop (); // НЕ БЕЗОПАСНО! Вы видите, что этот метод небезопасен
     * }
     * public void run() {
     *     while (true) {
     *         try {
     *             Thread.sleep(interval);
     *         } catch (InterruptedException e){
     *         }
     *         repaint();
     *     }
     * }
     * You can avoid the use of Thread.stop by replacing the applet's stop and run methods with:
     *  Вы можете избежать использования метода Thread.stop, заменив методы stop и run приложения следующим образом.
     * private volatile Thread blinker;
     * public void stop() {
     *     blinker = null;
     * }
     * public void run() {
     *     Thread thisThread = Thread.currentThread();
     *     while (blinker == thisThread) {
     *         try {
     *             Thread.sleep(interval);
     *         } catch (InterruptedException e){
     *         }
     *         repaint();
     *     }
     * }
     *  Вышеуказанный метод обработки проблематичен, если поток находится в нерабочем состоянии (когда вызывается спящий метод или когда ожидание
     *  Метод вызывается или блокируется IO), вышеуказанный метод недоступен. В это время вы можете использовать метод прерывания, чтобы сломать блок
     *  Ситуация, когда метод прерывания прерывает ситуацию блокировки, когда прерывание вызывается, Inetrrupted будет выброшено
     *  Исключение: вы можете безопасно завершить поток, перехватив это исключение в методе run.
     * How do I stop a thread that waits for long periods (e.g., for input)?
     * That's what the Thread.interrupt method is for. The same "state based"
     * signaling mechanism shown above can be used, but the state change (blinker = null
     * , in the previous example) can be followed by a call to Thread.interrupt,
     * to interrupt the wait:
     * public void stop() {
     *     Thread moribund = waiter;
     *     waiter = null;
     *     moribund.interrupt();
     * }
     * For this technique to work, it's critical that any method that catches an interrupt
     * exception and is not prepared to deal with it immediately reasserts the exception.
     * We say reasserts rather than rethrows, because it is not always possible to rethrow
     * the exception. If the method that catches the InterruptedException is not declared
     * to throw this (checked) exception, then it should "reinterrupt itself" with the
     * following incantation:
     *     Thread.currentThread().interrupt();
     * This ensures that the Thread will reraise the InterruptedException as soon as it is able.
     *  Как мне остановить поток, который должен долго ждать (например, ждать ввода)?
     *  Это цель метода Thread.interrupt, вы можете использовать тот же механизм сигналов, основанный на состоянии, как показано выше, но статус
     *  Изменение состояния может вызвать только метод прерывания для прерывания ожидания:
     *  Чтобы этот метод работал, любое исключение, которое перехватывает InterruptedException, является критическим и не готово немедленно
     * Обработайте это исключение. Мы говорим, скорее, переосмысливая, чем перебрасывая, потому что не всегда возможно отбросить исключение. Если InterrupedException пойман
     *  Метод исключения не объявляет, что это исключение проверки выброшено, тогда он должен прервать себя снова следующим методом:
     * 	Thread.currentThread().interrupt();
     *  Это гарантирует, что поток немедленно запускает InterruptedException, когда это разрешено.
     * What if a thread doesn't respond to Thread.interrupt?
     * In some cases, you can use application specific tricks. For example, if a thread is
     * waiting on a known socket, you can close the socket to cause the thread to return
     * immediately. Unfortunately, there really isn't any technique that works in general.
     * It should be noted that in all situations where a waiting thread doesn't respond to
     * Thread.interrupt, it wouldn't respond to Thread.stop either. Such cases include
     * deliberate denial-of-service attacks, and I/O operations for which thread.stop and
     * thread.interrupt do not work properly.
     *  Что если поток не отвечает на метод Thread.interrupt?
     *  В некоторых случаях вы можете использовать определенные методы. Например, если поток ожидает на известном сокете, вы можете закрыть сокет
     *  Слово, чтобы поток вернулся немедленно. К сожалению, в целом эффективной технологии нет. Следует отметить, что в
     *  Во всех случаях, когда ожидающий поток не отвечает на метод Thread.interrupt, он не отвечает на метод Thread.stop. Этот вид
     *  В случаях, включая преднамеренные атаки типа «отказ в обслуживании» и операции ввода-вывода, методы thread.stop и thread.interrupt не могут быть правильными
     *  Работать.
     * Why are Thread.suspend and Thread.resume deprecated?
     * Thread.suspend is inherently deadlock-prone. If the target thread holds a lock on
     * the monitor protecting a critical system resource when it is suspended, no thread
     * can access this resource until the target thread is resumed. If the thread that
     * would resume the target thread attempts to lock this monitor prior to calling resume,
     * deadlock results. Such deadlocks typically manifest themselves as "frozen" processes.
     *  Почему исключены методы Thread.suspend и Thread.resume?
     *  Метод Thread.suspend по своей природе подвержен тупикам. Когда целевой поток вызывает метод Thread.suspend, если он находится на мониторе
     *  Имейте блокировку для защиты важных системных ресурсов, тогда, пока целевой поток не вызовет метод Thread.resume, ни один поток не сможет получить доступ к этому ресурсу
     *  Источник. Если для возобновления целевого потока будет вызван метод возобновления, этот поток пытается заблокировать монитор перед вызовом метода Thread.resume.
     *  , Вызовет тупик. Этот вид тупика обычно проявляется как «замороженный» процесс.
     * What should I use instead of Thread.suspend and Thread.resume?
     * As with Thread.stop, the prudent approach is to have the "target thread" poll a variable
     * indicating the desired state of the thread (active or suspended). When the desired state
     * is suspended, the thread waits using Object.wait. When the thread is resumed, the target
     * thread is notified using Object.notify.
     *  Что я должен использовать вместо методов Thread.suspend и Thread.resume?
     *  Точно так же, как метод Thread.stop, разумный подход заключается в том, чтобы «целевой поток» опрашивал переменную, которая указывает желаемое состояние потока
     *  Состояние (то есть рабочее состояние) или приостановленное состояние). Когда желаемое состояние приостановлено, поток использует метод Object.wait для ожидания.
     *  Метод Object суперкласса определяет метод wait () и методы notify и notifyAll (). Кроме того, другие объекты наследуют этот метод
     *  Метод ожидания, текущий поток снимет блокировку). Когда поток возобновляется, используйте метод Object.notify, чтобы уведомить поток.
     * For example, suppose your applet contains the following mousePressed event handler, which
     * toggles the state of a thread called blinker:
     *  Например, предположим, что ваше приложение включает в себя обработчик для события мыши, которое переключает состояние потока с именем bliker:
     *  private boolean threadSuspended; // Это флаг для приостановки и возобновления потока
     * Public void mousePressed(MouseEvent e) {
     *     e.consume();
     *     if (threadSuspended)
     *         blinker.resume();
     *     else
     *         blinker.suspend();  // DEADLOCK-PRONE!
     *     threadSuspended = !threadSuspended;
     * }
     * You can avoid the use of Thread.suspend and Thread.resume by replacing the event handler above with:
     *  Вы можете избежать использования методов Thread.suspend и Thread.resume, заменив обработчик событий следующим
     * public synchronized void mousePressed(MouseEvent e) {
     *     e.consume();
     *     threadSuspended = !threadSuspended;
     *     if (!threadSuspended)
     *         notify();
     * }
     * and adding the following code to the "run loop":
     *  И добавьте следующий код в метод run ():
     * synchronized(this) {
     *     while (threadSuspended)
     *         wait();
     * }
     * The wait method throws the InterruptedException, so it must be inside a try ... catch
     * clause. It's fine to put it in the same clause as the sleep. The check should follow
     * (rather than precede) the sleep so the window is immediately repainted when the thread
     * is "resumed." The resulting run method follows:
     *  Метод wait () создает исключение InterruptedException, поэтому его необходимо включить в блок try-catch. Положи и спи () сбоку
     *  Возможно поместить метод в блок операторов. Проверка должна выполняться после вызова метода sleep () (не раньше), чтобы окно возобновлялось при возобновлении потока.
     *  Перерисовать немедленно. Пример кода метода run () выглядит следующим образом:
     * public void run() {
     *     while (true) {
     *         try {
     *             Thread.sleep(interval);
     *             synchronized(this) {
     *                 while (threadSuspended)
     *                     wait();
     *             }
     *         } catch (InterruptedException e){
     *         }
     *         repaint();
     *     }
     * }
     * Note that the notify in the mousePressed method and the wait in the run method are
     * inside synchronized blocks. This is required by the language, and ensures that wait
     * and notify are properly serialized. In practical terms, this eliminates race conditions
     * that could cause the "suspended" thread to miss a notify and remain suspended indefinitely.
     * While the cost of synchronization in Java is decreasing as the platform matures, it
     * will never be free. A simple trick can be used to remove the synchronization that
     * we've added to each iteration of the "run loop." The synchronized block that was
     * added is replaced by a slightly more complex piece of code that enters a synchronized
     * block only if the thread has actually been suspended:
     *  Примечание: метод notify в методе mousePressed и метод wait в методе run находятся в синхронизированном блоке.
     *  Что нужно языку, и убедитесь, что ожидания и уведомления правильно сериализованы. Фактически, это устраняет условие гонки, и это условие гонки
     *  Условия могут привести к тому, что «зависшие» потоки пропустят уведомления и будут зависать бесконечно. Хотя по мере развития платформы стоимость синхронизации в Java
     *  Продолжайте падать, но никогда не будет никаких накладных расходов. Вы можете использовать простой трюк для удаления синхронизации, мы можем добавить метод запуска
     *  Каждая итерация. Добавленный блок синхронизации заменяется немного более сложным кодом, только если поток фактически приостановлен
     *  Введите блок синхронизированного кода.
     * In the absence of explicit synchronization, threadSuspended must be made volatile to
     * ensure prompt communication of the suspend-request.
     * The resulting run method is:
     *  В отсутствие синхронизации переменная threadSuspended должна быть изменена с ключевым словом volatile, чтобы обеспечить запрос suspend
     *  Общайтесь во времени.
     * private volatile boolean threadSuspended;
     * public void run() {
     *     while (true) {
     *         try {
     *             Thread.sleep(interval);
     *             if (threadSuspended) {
     *                 synchronized(this) {
     *                     while (threadSuspended)
     *                         wait();
     *                 }
     *             }
     *         } catch (InterruptedException e){
     *         }
     *         repaint();
     *     }
     * }
     * Can I combine the two techniques to produce a thread that may be safely "stopped"
     * or "suspended"?
     *  Могу ли я объединить эти два метода для создания безопасной «остановленной» или «приостановленной» нити?
     * Yes, it's reasonably straightforward. The one subtlety is that the target thread
     * may already be suspended at the time that another thread tries to stop it. If the
     * stop method merely sets the state variable (blinker) to null, the target thread
     * will remain suspended (waiting on the monitor), rather than exiting gracefully
     * as it should. If the applet is restarted, multiple threads could end up waiting
     * on the monitor at the same time, resulting in erratic behavior.
     * To rectify this situation, the stop method must ensure that the target thread resumes
     * immediately if it is suspended. Once the target thread resumes, it must recognize
     * immediately that it has been stopped, and exit gracefully. Here's how the resulting
     * run and stop methods look:
     *  Да, его реализация очень проста. Тонкость заключается в том, что целевой поток может быть приостановлен, когда другой поток пытается остановить его.
     *  Если метод stop () просто устанавливает нулевое состояние переменной blinker, целевой поток останется приостановленным (в ожидании монитора) вместо
     *  Выходите изящно, когда это возможно. Если приложение перезапускается, несколько потоков могут перестать ожидать монитор одновременно, что приводит к нестабильной работе
     *  Поведение. Чтобы исправить эту ситуацию, пользовательский метод stop () должен обеспечить немедленное возобновление возобновления целевого потока. Однажды цель
     *  Когда поток возобновляется, он должен немедленно распознать и прекратить изящный выход. Ниже перечислены определенные методы run и stop:
     * public void run() {
     *     Thread thisThread = Thread.currentThread();
     *     while (blinker == thisThread) {
     *         try {
     *             Thread.sleep(interval);
     *             synchronized(this) {
     *                 while (threadSuspended && blinker==thisThread)
     *                     wait();
     *             }
     *         } catch (InterruptedException e){
     *         }
     *         repaint();
     *     }
     * }
     * public synchronized void stop() {
     *     blinker = null;
     *     notify();
     * }
     * If the stop method calls Thread.interrupt, as described above, it needn't call
     * notify as well, but it still must be synchronized. This ensures that the target
     * thread won't miss an interrupt due to a race condition.
     *  Как описано выше, если пользовательский метод stop () вызывает метод Thread.interrupt (), вам не нужно вызывать notify
     *  () Метод, но он все равно должен быть синхронизирован. Это гарантирует, что целевой поток не пропустит прерывания из-за условий гонки.
     * What about Thread.destroy?
     * Thread.destroy was never implemented and has been deprecated. If it were implemented,
     * it would be deadlock-prone in the manner of Thread.suspend. (In fact, it is roughly
     * equivalent to Thread.suspend without the possibility of a subsequent Thread.resume.)
     *  А как насчет метода Thread.destroy?
     * Thread.destroy никогда не был реализован и сейчас устарел. Если это реализовано, это может легко вызвать тупик. (На самом деле, нет никаких последующих
     *  Когда поток приостановлен, он примерно такой же, как метод Thread.suspend).
     * Why is Runtime.runFinalizersOnExit deprecated?
     * Because it is inherently unsafe. It may result in finalizers being called on live objects
     * while other threads are concurrently manipulating those objects, resulting in erratic
     * behavior or deadlock. While this problem could be prevented if the class whose objects
     * are being finalized were coded to "defend against" this call, most programmers do not
     * defend against it. They assume that an object is dead at the time that its finalizer is called.
     * Further, the call is not "thread-safe" in the sense that it sets a VM-global flag. This
     * forces every class with a finalizer to defend against the finalization of live objects!
     *  Почему Runtime.runFinalizersOnExit считается устаревшим?
     *  Потому что это небезопасно. Это заставляет финализатор вызываться на активном объекте. Когда другие потоки работают с этими объектами одновременно, это вызовет
     *  Стабильность или тупик. Однако этой проблемы можно избежать, если класс, в котором завершается объект силы, закодирован как вызов «защиты». Большинство программ
     *  Сотрудники не сопротивлялись этому. Они предполагают, что объект умер, когда вызывается программа завершения. Кроме того, в смысле установки глобального флага VM, вызов не
     *  «Поток безопасно». Это заставляет каждый класс использовать финализаторы для защиты от конца активных объектов! "" "
     */
}
