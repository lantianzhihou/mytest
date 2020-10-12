package com.wangbo.test.controller;

import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class FileConfigController {
	/**
	 * 	@InitBinder作用于@Controller中的方法，表示为当前控制器注册一个属性编辑器，对WebDataBinder进行初始化,且只对当前的Controller有效。
	 * 	解析的时机：
	 * 	是其所标注的方法，在该方法被请求执行之前。同时@InitBinder标注的方法是可以多次执行的，也就是说来一次请求就执行一次@InitBinder解析。
	 * 	执行原理：
	 *      当某个Controller上的第一次请求，由SpringMVC前端控制器匹配到该Controller之后，根据Controller的
	 *      class类型来查找所有标注了@InitBinder注解的方法，并且存入RequestMappingHandlerAdapter里的
	 *      initBinderCache缓存中。等下一次请求执行对应业务方法之前,会先走initBinderCache缓存,而不用再去解析@InitBinder。
	 */
	
	// 解除controller返回list的size大小
	@InitBinder
	protected void initBinder(WebDataBinder binder) {
		binder.setAutoGrowNestedPaths(true);
		binder.setAutoGrowCollectionLimit(2048);
	}
}
