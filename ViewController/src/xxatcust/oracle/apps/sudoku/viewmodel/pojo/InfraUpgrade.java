package xxatcust.oracle.apps.sudoku.viewmodel.pojo;

import java.util.List;

public class InfraUpgrade {
    List<ConfiguratorNodePOJO> infraUpgradeItems ;
    public InfraUpgrade() {
        super();
    }

    public void setInfraUpgradeItems(List<ConfiguratorNodePOJO> infraUpgradeItems) {
        this.infraUpgradeItems = infraUpgradeItems;
    }

    public List<ConfiguratorNodePOJO> getInfraUpgradeItems() {
        return infraUpgradeItems;
    }
}
