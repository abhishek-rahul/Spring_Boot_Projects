package com.abhicom.userservice.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

public class CheckoutRequest {

    @NotNull
    @Min(1)
    private Long amount; // keep simple: rupees/units

    @NotNull
    private FailureMode failureMode = FailureMode.NONE;

    /**
     * Only relevant when failureMode == CHECKED:
     * false -> checked exception will NOT rollback (default Spring rule)
     * true  -> checked exception WILL rollback (via rollbackFor demo method)
     */
    private boolean rollbackOnChecked = false;

    public enum FailureMode {
        NONE,
        RUNTIME,
        CHECKED
    }

    public Long getAmount() { return amount; }
    public void setAmount(Long amount) { this.amount = amount; }

    public FailureMode getFailureMode() { return failureMode; }
    public void setFailureMode(FailureMode failureMode) { this.failureMode = failureMode; }

    public boolean isRollbackOnChecked() { return rollbackOnChecked; }
    public void setRollbackOnChecked(boolean rollbackOnChecked) { this.rollbackOnChecked = rollbackOnChecked; }
}
