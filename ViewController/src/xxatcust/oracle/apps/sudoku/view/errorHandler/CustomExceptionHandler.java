package xxatcust.oracle.apps.sudoku.view.errorHandler;

import oracle.adf.controller.ControllerContext;
import oracle.adf.controller.ViewPortContext;
import oracle.adf.model.BindingContext;
import oracle.adf.model.binding.DCBindingContainer;
import oracle.adf.model.binding.DCErrorHandlerImpl;

public class CustomExceptionHandler extends DCErrorHandlerImpl {
    public CustomExceptionHandler() {
        super(true);
    }

    @Override
    public String getDisplayMessage(BindingContext ctx, Exception th) {
        return super.getDisplayMessage(ctx, th);
    }

    @Override
    public void reportException(DCBindingContainer dCBindingContainer,
                                Exception exception) {
        System.out.println("In DC Exception Handler "+exception.getMessage());
        super.reportException(dCBindingContainer, exception);
    }
}
