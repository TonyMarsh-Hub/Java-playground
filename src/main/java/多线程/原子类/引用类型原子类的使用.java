package 多线程.原子类;

import java.util.concurrent.atomic.AtomicMarkableReference;
import java.util.concurrent.atomic.AtomicReference;
import java.util.concurrent.atomic.AtomicStampedReference;

public class 引用类型原子类的使用 {
    public static void main(String[] args) {

    }
}

/*引用类型的原子类*/

record Person(String name, int age) {
}

class AtomicReferenceTest {
    public static void main(String[] args) {
        AtomicReference<Person> ar = new AtomicReference<>();
        Person person = new Person("SnailClimb", 22);
        ar.set(person);
        Person updatePerson = new Person("Daisy", 20);
        ar.compareAndSet(person, updatePerson);

        System.out.println(ar.get().name());
        System.out.println(ar.get().age());
    }
}


class AtomicStampedReferenceDemo {
    public static void main(String[] args) {
        // 实例化、取当前值和 stamp 值
        final int initialRef = 0, initialStamp = 0;
        final AtomicStampedReference<Integer> asr = new AtomicStampedReference<>(initialRef, initialStamp);
        System.out.println("currentReference=" + asr.getReference() + "\t currentStamp=" + asr.getStamp());

        // compare and set
        final int newReference = 666, newStamp = 999;
        final boolean casResult = asr.compareAndSet(initialRef, newReference, initialStamp, newStamp);
        System.out.println("currentReference=" + asr.getReference()
                + "\t currentStamp=" + asr.getStamp()
                + "\t casResult=" + casResult);

        // 获取当前的值和当前的 stamp 值
        int[] arr = new int[1];
        final Integer currentReference = asr.get(arr);
        final int currentStamp = arr[0];
        System.out.println("currentReference=" + currentReference + "\t currentStamp=" + currentStamp);
        /* get()的使用好像有些复杂，为什么要做这些设计？

            首先需要解释的是： 虽然AtomicStampedReference提供了分别用于获取当前值和stamp的方法: getReference() 和 getStamp()，
            但在大部分的多线程开发场景下，其不被推荐使用，因为这两个数据应当是强绑定关系的，分开获取会导致数据不一致的问题。

            其次，为什么要采取 Value直接返回，而stamp放在一个数组里的设计呢？
            1. 性能考虑，这样的做法可以避免创建一个新的对象(Wrapper)，从而避免了内存分配的开销。
            2. 设计习惯，这种设计方式在java.util.concurrent.atomic包中已经有了很多的实现，比如AtomicIntegerArray，AtomicLongArray等，
               为了保持一致性，AtomicStampedReference也采用了这种设计方式。
            3. 可拓展性，如果将来有新的需求，需要获取更多的数据，那么只需要在数组中添加新的元素即可，而不需要修改原有的方法调用说明。
          */

        // 之后的代码中为了演示方便就直接用 getReference() 和 getStamp() 了，但注意在多线程环境下不推荐这么做

        // 单独设置 stamp 值
        final boolean attemptStampResult = asr.attemptStamp(newReference, 88);
        System.out.println("currentReference=" + asr.getReference()
                + "\t currentStamp=" + asr.getStamp()
                + "\t attemptStampResult=" + attemptStampResult);

        // 重新设置当前值和 stamp 值
        asr.set(initialRef, initialStamp);
        System.out.println("currentReference=" + asr.getReference() + "\t currentStamp=" + asr.getStamp());

        /*注意
        * 在多线程环境下 应当避免使用 get() 方法，而使用CompareAndSet()方法来更新引用和标记。
        * 同时，使用attemptStamp的情况下，需要确保单独更新时间戳不会导致并发问题。如果同时更新引用和时间戳更安全，请使用 compareAndSet。
        * */

        /*weakCompareAndSet 方法与 compareAndSet 类似，都是用于原子地比较并设置引用及其关联的时间戳。
        然而，它们之间有一个关键区别：weakCompareAndSet 的操作可以自由地被重排或重试。
        这意味着在某些情况下，weakCompareAndSet 可能会失败，即使引用和时间戳满足预期条件。

        这个方法的主要用途是为了提高性能。
        在某些体系结构上，weakCompareAndSet 的实现可能比 compareAndSet 更高效。
        因此，在不关心偶尔失败的情况下，可以考虑使用 weakCompareAndSet 以提高性能。

        然而，在使用 weakCompareAndSet 时需谨慎，因为它可能会导致操作失败，即使引用和时间戳满足预期条件。
        这种失败可能会导致循环中的额外迭代，从而影响性能。因此，你需要仔细评估你的具体场景，以确定 weakCompareAndSet 是否适用。

        一般来说，在以下情况下可以考虑使用 weakCompareAndSet：

        你可以容忍偶尔的失败，例如在一个自旋锁实现中，失败只会导致额外的迭代。
        你的场景不涉及顺序敏感的操作，因为 weakCompareAndSet 可能被重排。
        你的硬件平台支持 weakCompareAndSet 的高效实现。
        如果你的场景不满足以上条件，建议使用 compareAndSet 以确保更可靠的行为。*/

        final boolean wCasResult = asr.weakCompareAndSet(initialRef, newReference, initialStamp, newStamp);
        System.out.println("currentReference=" + asr.getReference()
                + "\t currentStamp=" + asr.getStamp()
                + "\t wCasResult=" + wCasResult);
    }
}













/*AtomicMarkableReference 和 AtomicStampedReference 都是用于解决并发环境下的 ABA 问题，但它们的关注点和使用场景略有不同。

AtomicMarkableReference 主要用于跟踪引用及其关联的布尔标记（mark），而不是时间戳。这在某些场景下是有用的，例如，你可能需要跟踪一个对象是否被标记为“已删除”或“已修改”。

AtomicStampedReference 则关注于引用和关联的整数时间戳。时间戳可以表示更多信息，如版本号、计数器等。这使得 AtomicStampedReference 在需要更丰富的信息时更有优势。

总结一下，它们之间的主要差异如下：

关注点：AtomicMarkableReference 关注于布尔标记，而 AtomicStampedReference 关注于整数时间戳。
使用场景：AtomicMarkableReference 适用于需要跟踪对象的布尔状态的场景，如“已删除”或“已修改”。而 AtomicStampedReference 适用于需要跟踪对象的整数信息的场景，如版本号、计数器等。
选择哪个类取决于你的具体需求。如果你只需要跟踪一个布尔标记，那么 AtomicMarkableReference 可能是更好的选择。如果你需要跟踪整数信息，那么 AtomicStampedReference 更合适。*/

class AtomicMarkableReferenceDemo {
    public static void main(String[] args) {
        // 实例化、取当前值和 mark 值
        final Boolean initialRef = null, initialMark = false;
        final AtomicMarkableReference<Boolean> amr = new AtomicMarkableReference<>(initialRef, initialMark);
        System.out.println("currentReference=" + amr.getReference() + ", currentMark=" + amr.isMarked());

        // compare and set
        final Boolean newReference1 = true, newMark1 = true;
        final boolean casResult = amr.compareAndSet(initialRef, newReference1, initialMark, newMark1);
        System.out.println("currentReference=" + amr.getReference()
                + ", currentMark=" + amr.isMarked()
                + ", casResult=" + casResult);

        // 获取当前的值和当前的 mark 值
        boolean[] arr = new boolean[1];
        final Boolean currentReference = amr.get(arr);
        final boolean currentMark = arr[0];
        System.out.println("currentReference=" + currentReference + ", currentMark=" + currentMark);

        // 单独设置 mark 值
        final boolean attemptMarkResult = amr.attemptMark(newReference1, false);
        System.out.println("currentReference=" + amr.getReference()
                + ", currentMark=" + amr.isMarked()
                + ", attemptMarkResult=" + attemptMarkResult);

        // 重新设置当前值和 mark 值
        amr.set(initialRef, initialMark);
        System.out.println("currentReference=" + amr.getReference() + ", currentMark=" + amr.isMarked());

        // [不推荐使用，除非搞清楚注释的意思了] weak compare and set
        // 困惑！weakCompareAndSet 这个方法最终还是调用 compareAndSet 方法。[版本: jdk-8u191]
        // 但是注释上写着 "May fail spuriously and does not provide ordering guarantees,
        // so is only rarely an appropriate alternative to compareAndSet."
        // todo 感觉有可能是 jvm 通过方法名在 native 方法里面做了转发
        final boolean wCasResult = amr.weakCompareAndSet(initialRef, newReference1, initialMark, newMark1);
        System.out.println("currentReference=" + amr.getReference()
                + ", currentMark=" + amr.isMarked()
                + ", wCasResult=" + wCasResult);
    }
}


