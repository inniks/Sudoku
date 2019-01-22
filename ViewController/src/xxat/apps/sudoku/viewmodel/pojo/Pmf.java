package xxat.apps.sudoku.viewmodel.pojo;

import java.util.ArrayList;

import java.util.HashMap;

import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "pmf")
public class Pmf {
    
    List < PogoMappingFile > pmfMap ;
    public Pmf() {
       
        super();
        System.out.println("PMF Constructor Called "+pmfMap);
    }



    public void setPmfMap(List<PogoMappingFile> pmfMap) {
        this.pmfMap = pmfMap;
    }
    @XmlElement(name = "map")
    public List<PogoMappingFile> getPmfMap() {
        return pmfMap;
    }
}
