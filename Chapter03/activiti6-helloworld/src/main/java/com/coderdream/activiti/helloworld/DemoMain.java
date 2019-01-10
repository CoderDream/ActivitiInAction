package com.coderdream.activiti.helloworld;

import org.activiti.engine.*;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.repository.DeploymentBuilder;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public class DemoMain {

    /**
     * logger
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(DemoMain.class);

    public static void main(String[] args) {
        LOGGER.info("启动我们的程序");
        // 创建流程引擎
        ProcessEngineConfiguration cfg = ProcessEngineConfiguration.createStandaloneInMemProcessEngineConfiguration();
        ProcessEngine processEngine = cfg.buildProcessEngine();
        String name = processEngine.getName();
        String version = ProcessEngine.VERSION;
        LOGGER.info("流程引擎名称{}，版本{}", name, version);
        // 部署流程定义文件
        RepositoryService repositoryService = processEngine.getRepositoryService();
        DeploymentBuilder deploymentBuilder = repositoryService.createDeployment();
        deploymentBuilder.addClasspathResource("second_approve.bpmn");
        Deployment deployment = deploymentBuilder.deploy();
        String deploymentId = deployment.getId();
        ProcessDefinition processDefinition = repositoryService
                .createProcessDefinitionQuery()
                .deploymentId(deploymentId)
                .singleResult();
        LOGGER.info("流程定义文件 [{}]，流程ID [{}]", processDefinition.getName(), processDefinition.getId());
        // 启动运行流程
        RuntimeService runtimeService = processEngine.getRuntimeService();
        ProcessInstance processInstance = runtimeService.startProcessInstanceById(processDefinition.getId());
        LOGGER.info("启动流程 [{}]", processInstance.getProcessDefinitionKey());
        // 处理流程任务
        TaskService taskService = processEngine.getTaskService();
        List<Task> list = taskService.createTaskQuery().list();
        for (Task task : list) {
            LOGGER.info("待处理任务 [{}]", task.getName());
        }
        LOGGER.info("待处理任务数量 [{}]", list.size());
        LOGGER.info("结束我们的程序");
    }
}
