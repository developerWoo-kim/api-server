//package gw.apiserver.common.utils.logger;
//
//import org.aspectj.lang.ProceedingJoinPoint;
//import org.aspectj.lang.annotation.Around;
//import org.aspectj.lang.annotation.Aspect;
//import org.aspectj.lang.annotation.Pointcut;
//import org.springframework.stereotype.Component;
//
//@Aspect
//@Component
//public class RepositoryExecutionTimeLogger {
//
//    @Pointcut("execution(* gw.apiserver..repository..*.*(..))")
//    public void repositoryMethods() {
//        // 이 메서드는 포인트컷 표현식을 위한 자리 표시자입니다.
//    }
//
//    @Around("repositoryMethods()")
//    public Object logExecutionTime(ProceedingJoinPoint joinPoint) throws Throwable {
//        long startTime = System.currentTimeMillis();
//        Object result = joinPoint.proceed();
//        long endTime = System.currentTimeMillis();
//
//        long executionTime = endTime - startTime;
//
//        // 로깅 형식을 원하는 대로 조절하세요
//        System.out.println(joinPoint.getSignature() + "이(가) " + executionTime + "ms 동안 실행되었습니다.");
//
//        return result;
//    }
//}
