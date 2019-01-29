package xxatcust.oracle.apps.sudoku.util;

import java.io.File;

import java.io.InputStream;

import javax.xml.bind.JAXBContext;

import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

import org.apache.myfaces.trinidad.model.UploadedFile;

import xxatcust.oracle.apps.sudoku.viewmodel.pojo.Config;
import xxatcust.oracle.apps.sudoku.viewmodel.pojo.Contract;
import xxatcust.oracle.apps.sudoku.viewmodel.pojo.Cooling;
import xxatcust.oracle.apps.sudoku.viewmodel.pojo.Customer;
import xxatcust.oracle.apps.sudoku.viewmodel.pojo.Deal;
import xxatcust.oracle.apps.sudoku.viewmodel.pojo.Digital;
import xxatcust.oracle.apps.sudoku.viewmodel.pojo.Docking;
import xxatcust.oracle.apps.sudoku.viewmodel.pojo.Dps;
import xxatcust.oracle.apps.sudoku.viewmodel.pojo.DutifUtil;
import xxatcust.oracle.apps.sudoku.viewmodel.pojo.Infra;
import xxatcust.oracle.apps.sudoku.viewmodel.pojo.Mani;
import xxatcust.oracle.apps.sudoku.viewmodel.pojo.ModelBom;
import xxatcust.oracle.apps.sudoku.viewmodel.pojo.Pmf;
import xxatcust.oracle.apps.sudoku.viewmodel.pojo.PogoMappingFile;
import xxatcust.oracle.apps.sudoku.viewmodel.pojo.QHeader;
import xxatcust.oracle.apps.sudoku.viewmodel.pojo.Ruleset;
import xxatcust.oracle.apps.sudoku.viewmodel.pojo.Salesteam;
import xxatcust.oracle.apps.sudoku.viewmodel.pojo.SwLicenses;
import xxatcust.oracle.apps.sudoku.viewmodel.pojo.Thead;
import xxatcust.oracle.apps.sudoku.viewmodel.pojo.V93kQuote;
import xxatcust.oracle.apps.sudoku.viewmodel.pojo.Wksta;
import xxatcust.oracle.apps.sudoku.viewmodel.pojo.WtySupport;
import xxatcust.oracle.apps.sudoku.viewmodel.pojo.XClass;


public class JaxbParser {
    public JaxbParser() {
        super();
    }


    public static V93kQuote jaxbXMLToObject(InputStream inputStream) {
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
            V93kQuote parent = (V93kQuote)un.unmarshal(inputStream);
            ADFUtils.setPageFlowScopeValue("parentObject", parent);
            return parent;
        } catch (JAXBException e) {
            e.printStackTrace();
        }
        return null;
    }

}
