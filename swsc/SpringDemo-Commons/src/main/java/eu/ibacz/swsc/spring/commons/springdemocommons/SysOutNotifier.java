package eu.ibacz.swsc.spring.commons.springdemocommons;



public class SysOutNotifier implements Notifier {

    public void notify(String message) {
        System.out.println(message);
    }
    
}
