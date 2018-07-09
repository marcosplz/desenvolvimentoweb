package br.com.project.annotation;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

@Documented
@Retention(RUNTIME)
@Target(FIELD)
public abstract @interface IdentificaCampoPesquisa {
	
	String descricaoCampo(); // Descrição do campo para a tela
	String campoConsulta(); //Campo do banco de dados
	int principal() default 10000; //Posição de destaque no combo
}
