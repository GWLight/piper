/* 
 * Copyright (C) Creactiviti LLC - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Written by Arik Cohen <arik@creactiviti.com>, Apr 2017
 */
package com.creactiviti.piper.core.event;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.PayloadApplicationEvent;
import org.springframework.stereotype.Component;

import com.creactiviti.piper.config.ConditionalOnCoordinator;
import com.creactiviti.piper.core.job.MutableJobTask;
import com.creactiviti.piper.core.task.JobTask;
import com.creactiviti.piper.core.task.JobTaskRepository;
import com.creactiviti.piper.core.task.TaskStatus;

/**
 * 
 * @author Arik Cohen
 * @since Apt 9, 2017
 */
@Component
@ConditionalOnCoordinator
public class TaskStartedEventHandler implements ApplicationListener<PayloadApplicationEvent<PiperEvent>> {

  private JobTaskRepository jobTaskRepository;

  @Override
  public void onApplicationEvent(PayloadApplicationEvent<PiperEvent> aEvent) {
    PiperEvent event = aEvent.getPayload();
    if(Events.TASK_STARTED.equals(event.getType())) {
      String taskId = event.getString("taskId");
      JobTask task = jobTaskRepository.findOne(taskId);
      MutableJobTask mtask = new MutableJobTask(task);
      mtask.setStatus(TaskStatus.STARTED);
      jobTaskRepository.update(mtask);
    }
  }

  @Autowired
  public void setJobTaskRepository(JobTaskRepository aJobTaskRepository) {
    jobTaskRepository = aJobTaskRepository;
  }
  
}