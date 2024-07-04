package org.zkpk.cs.config.transaction;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import org.springframework.aop.aspectj.AspectJExpressionPointcutAdvisor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.interceptor.NameMatchTransactionAttributeSource;
import org.springframework.transaction.interceptor.RollbackRuleAttribute;
import org.springframework.transaction.interceptor.RuleBasedTransactionAttribute;
import org.springframework.transaction.interceptor.TransactionAttribute;
import org.springframework.transaction.interceptor.TransactionAttributeSource;
import org.springframework.transaction.interceptor.TransactionInterceptor;

@Configuration
public class TxAnoConfiguration {
	
	/**
	 * 
	 * @Description: 事务拦截类型
	 * @author HUCHAO
	 * @date 2019-02-27 17:58:21
	 * @return
	 */
	@Bean("txSource")
    public TransactionAttributeSource transactionAttributeSource(){
        NameMatchTransactionAttributeSource source = new NameMatchTransactionAttributeSource();
         /*只读事务，不做更新操作*/
        RuleBasedTransactionAttribute readOnlyTx = new RuleBasedTransactionAttribute();
        readOnlyTx.setReadOnly(true);
        readOnlyTx.setPropagationBehavior(TransactionDefinition.PROPAGATION_NOT_SUPPORTED);
        /*当前存在事务就使用当前事务，当前不存在事务就创建一个新的事务*/
        //RuleBasedTransactionAttribute requiredTx = new RuleBasedTransactionAttribute();
        //requiredTx.setRollbackRules(
        //    Collections.singletonList(new RollbackRuleAttribute(Exception.class)));
        //requiredTx.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);
        RuleBasedTransactionAttribute requiredTx = new RuleBasedTransactionAttribute(TransactionDefinition.PROPAGATION_REQUIRED,
            Collections.singletonList(new RollbackRuleAttribute(Exception.class)));
        //requiredTx.setTimeout(5);
        Map<String, TransactionAttribute> txMap = new HashMap<>();
        txMap.put("select*", readOnlyTx);
        txMap.put("get*", readOnlyTx);
        txMap.put("find*", readOnlyTx);
        txMap.put("delete*", requiredTx);
        txMap.put("update*", requiredTx);
        txMap.put("insert*", requiredTx);
        txMap.put("save*", requiredTx);
        txMap.put("batch*", requiredTx);
        txMap.put("*", requiredTx);
        source.setNameMap( txMap );
 
        return source;
    }
 
    /**
     * 
     * @Description: 切面拦截规则 参数会自动从容器中注入
     * @author HUCHAO
     * @date 2019-02-27 17:58:00
     * @param txInterceptor
     * @return
     */
    @Bean
    public AspectJExpressionPointcutAdvisor pointcutAdvisor(TransactionInterceptor txInterceptor){
        AspectJExpressionPointcutAdvisor pointcutAdvisor = new AspectJExpressionPointcutAdvisor();
        pointcutAdvisor.setAdvice(txInterceptor);
        pointcutAdvisor.setExpression("execution (* org.zkpk.*.service.impl..*+.*(..))");
        return pointcutAdvisor;
    }
 
    /**
     * 
     * @Description: 事务拦截器
     * @author HUCHAO
     * @date 2019-02-27 17:58:11
     * @param tx
     * @return
     */
    @Bean("txInterceptor")
    TransactionInterceptor getTransactionInterceptor(PlatformTransactionManager tx){
        return new TransactionInterceptor(tx , transactionAttributeSource()) ;
    }

}
