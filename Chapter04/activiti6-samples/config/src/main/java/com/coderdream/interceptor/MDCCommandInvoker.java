package com.coderdream.interceptor;

import org.activiti.engine.impl.agenda.AbstractOperation;
import org.activiti.engine.impl.interceptor.DebugCommandInvoker;
import org.activiti.engine.logging.LogMDC;

public class MDCCommandInvoker extends DebugCommandInvoker {

    // 参考DebugCommandInvoker代码
    @Override
    public void executeOperation(Runnable runnable) {
        // 保存默认状态
        boolean mdcEnabled = LogMDC.isMDCEnabled();
        // 修改状态
        LogMDC.setMDCEnabled(true);
        if (runnable instanceof AbstractOperation) {
            AbstractOperation operation = (AbstractOperation) runnable;

            if (operation.getExecution() != null) {
                // 记录日志
                LogMDC.putMDCExecution(operation.getExecution());
            }

        }

        super.executeOperation(runnable);
        // 清理上下文信息
        LogMDC.clear();
        // 还原为默认状态
        if (!mdcEnabled) {
            LogMDC.setMDCEnabled(false);
        }
    }
}
