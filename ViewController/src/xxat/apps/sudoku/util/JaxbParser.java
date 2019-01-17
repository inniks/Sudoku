package xxat.apps.sudoku.util;

import java.io.File;

import javax.xml.bind.JAXBContext;

import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

import xxat.apps.sudoku.viewmodel.pojo.*;

public class JaxbParser {
    public JaxbParser() {
        super();
    }


    public static V93kQuote jaxbXMLToObject() {
        File f =
            new File("D://Projects//Advantest//ImportConfigPage//V93000 C&Q 3.0 - XML File Example.xml");
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
            Pmf pmfObj = parent.getConfigObject().getPmfObject();
            for(PogoMappingFile pmf : pmfObj.getPmfMap()){
                    System.out.println(pmf.getRefId());
            }
            return parent;
        } catch (JAXBException e) {
            e.printStackTrace();
        }
        return null;
    }

}
