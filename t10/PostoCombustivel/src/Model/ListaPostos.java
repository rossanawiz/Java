/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;
import java.util.List;
import java.util.ArrayList;

/**
 *
 * @author Felipe
 */
public class ListaPostos{
    private List<Posto> list_postos;
    private CSVPosto csv;
    
    public ListaPostos(){
        list_postos = new ArrayList<Posto>();
        csv = new CSVPosto();
    }
    
    public void addPosto(Posto posto){
        list_postos.add(posto);
        csv.write_posto(posto);
    }
    
    public void removePosto(Posto posto){
        list_postos.remove(posto);
        
    }
    
    public void replacePosto(Posto old_posto, Posto new_posto){
        int index = list_postos.indexOf(old_posto);
        list_postos.remove(index);
        list_postos.add(index, new_posto);
    }
    
    public String[] get_namelist(){
        String[] name_list = new String[list_postos.size()];
        int i = 0;
        for(Posto p: list_postos){
            name_list[i] = p.get_nome();
            i++;
        }
        return name_list;
    }
    
    public List<Posto> get_list(){
        //Posto[] array = list_postos.toArray(new Posto[list_postos.size()]);
        return list_postos;
    }
    
    public List<Posto> busca_bairro(String bairro){
        List<Posto> lista_bairro = new ArrayList();
        for (Posto p : list_postos) {
            if(p.get_bairro().equals(bairro)){
                lista_bairro.add(p);
            }
        }
        return lista_bairro;
    }
}
