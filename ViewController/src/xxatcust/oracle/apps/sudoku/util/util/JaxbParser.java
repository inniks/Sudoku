package xxatcust.oracle.apps.sudoku.util.util;

import java.io.File;

import javax.xml.bind.JAXBContext;

import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

import xxatcust.oracle.apps.sudoku.viewmodel.viewmodel.pojo.Config;
import xxatcust.oracle.apps.sudoku.viewmodel.viewmodel.pojo.Contract;
import xxatcust.oracle.apps.sudoku.viewmodel.viewmodel.pojo.Cooling;
import xxatcust.oracle.apps.sudoku.viewmodel.viewmodel.pojo.Customer;
import xxatcust.oracle.apps.sudoku.viewmodel.viewmodel.pojo.Deal;
import xxatcust.oracle.apps.sudoku.viewmodel.viewmodel.pojo.Digital;
import xxatcust.oracle.apps.sudoku.viewmodel.viewmodel.pojo.Docking;
import xxatcust.oracle.apps.sudoku.viewmodel.viewmodel.pojo.Dps;
import xxatcust.oracle.apps.sudoku.viewmodel.viewmodel.pojo.DutifUtil;
import xxatcust.oracle.apps.sudoku.viewmodel.viewmodel.pojo.Infra;
import xxatcust.oracle.apps.sudoku.viewmodel.viewmodel.pojo.Mani;
import xxatcust.oracle.apps.sudoku.viewmodel.viewmodel.pojo.ModelBom;
import xxatcust.oracle.apps.sudoku.viewmodel.viewmodel.pojo.Pmf;
import xxatcust.oracle.apps.sudoku.viewmodel.viewmodel.pojo.PogoMappingFile;
import xxatcust.oracle.apps.sudoku.viewmodel.viewmodel.pojo.QHeader;
import xxatcust.oracle.apps.sudoku.viewmodel.viewmodel.pojo.Ruleset;
import xxatcust.oracle.apps.sudoku.viewmodel.viewmodel.pojo.Salesteam;
import xxatcust.oracle.apps.sudoku.viewmodel.viewmodel.pojo.SwLicenses;
import xxatcust.oracle.apps.sudoku.viewmodel.viewmodel.pojo.Thead;
import xxatcust.oracle.apps.sudoku.viewmodel.viewmodel.pojo.V93kQuote;
import xxatcust.oracle.apps.sudoku.viewmodel.viewmodel.pojo.Wksta;
import xxatcust.oracle.apps.sudoku.viewmodel.viewmodel.pojo.WtySupport;
import xxatcust.oracle.apps.sudoku.viewmodel.viewmodel.pojo.XClass;


public class JaxbParser {
    public JaxbParser() {
        super();
    }


    public static V93kQuote jaxbXMLToObject() {
        File f =
            new File("D://Projects//Advantest//ImportConfigPage//Test Case 001.xml");
        System.out.println("File found " + f.getName());
        try {
            JAXBContext context =
                JAXBContext.newInstance(V93kQuote.class, Config.class,
                                        QHeader.class, Customer.class,
                                        ModelBom.class, Contract.class,
                                        Cooling.class, Deal.class,
                                        Digital.class, Docking.class,
                                        Dps.class, DutifUtil.class,
                                        Infra.class, Mani.class, Ruleset.class,
                                        Salesteam.class, SwLicenses.class,
                                        Thead.class, Wksta.class,
                                        WtySupport.class, XClass.class,Pmf.class,PogoMappingFile.class);
            Unmarshaller un = context.createUnmarshaller();
            V93kQuote parent = (V93kQuote)un.unmarshal(f);
            ADFUtils.setPageFlowScopeValue("parentObject", parent);
            return parent;
        } catch (JAXBException e) {
            e.printStackTrace();
        }
        return null;
    }

}
