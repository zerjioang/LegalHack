package zerjioang.im.legalhack.ui.fragment.legal.sub_fragmentos;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import java.util.LinkedList;
import java.util.List;

import zerjioang.im.legalhack.R;
import zerjioang.im.legalhack.adapter.lists.help.HelpAdapter;
import zerjioang.im.legalhack.adapter.lists.help.HelpItemList;

/**
 * A simple {@link Fragment} subclass.
 */
public class PrivacidadFragment extends Fragment {


    private ListView listViewHelp;

    public PrivacidadFragment() {
        // Required empty public constructor
    }

    public static PrivacidadFragment newInstance() {
        PrivacidadFragment fragment = new PrivacidadFragment();
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_help, container, false);

        listViewHelp = (ListView) view.findViewById(R.id.listViewHelp);

        initLista();
        return view;
    }

    private void initLista() {
        List<HelpItemList> lista = new LinkedList<>();
        lista.add(new HelpItemList(
                "USO Y TRATAMIENTO DE DATOS DE CARACTER PERSONAL",
                "En cumplimiento de lo establecido en la Ley Org�nica 15/1999, de 13 de diciembre, de Protecci�n de Datos de Car�cter Personal (en adelante, LOPD), Esri le informa de que los datos de car�cter personal por Vd. proporcionados, as� como aquellos datos a los que se pueda acceder como consecuencia de su navegaci�n, ser�n incorporados a los ficheros automatizados de la entidad, pudiendo ejercitar su derecho de acceso, rectificaci�n, cancelaci�n y oposici�n al tratamiento de sus datos en los t�rminos y condiciones previstos m�s abajo, en el apartado 2�. En cumplimiento de la Ley 34/2002, de 11 de julio, de Servicios de la Sociedad de la Informaci�n y de Comercio Electr�nico, (en adelante LSSICE), Esri no enviar� por correo electr�nico comunicaciones publicitarias que no hayan sido autorizadas por los usuarios. Esri identificar� mediante la palabra publicidad aquellos correos o Newsletters que contengan comunicaciones comerciales o promociones, exclusivamente.\n" +
                        "En aquellos casos donde sea necesario cumplimentar un formulario (inscripci�n a seminarios o eventos), o remitir un correo electr�nico (contacta: informacion@esri.es Empleo: rrhh@esri.es) y hacer click en el bot�n de enviar, su realizaci�n implicar� necesariamente que haya sido informado (en virtud del art�culo 5 LOPD) y, en su caso, ha otorgado el correspondiente consentimiento (a tenor del art�culo 6 LOPD) al contenido de la presente pol�tica de privacidad.\n" +
                        "\n" +
                        "Por otra parte, mediante el registro de los datos, el usuario otorga su consentimiento al tratamiento de los mismos con las finalidades en cada caso se�aladas. Asimismo, el usuario autoriza la cesi�n de los datos recabados al resto de empresas de EPTISA al que Esri pertenece con fines publicitarios o comerciales.\n" +
                        "\n" +
                        "As�, siempre que ESri le solicite datos de car�cter personal, incluir� el correspondiente clausulado legal y un enlace (link) vinculado a la presente Pol�tica con el prop�sito de hacerle part�cipe de sus derechos y obligaciones establecidos en la LOPD y LSSIC."));
        lista.add(new HelpItemList(
                "EJERCICIO DE DERECHOS",
                "Aquellas personas f�sicas que hayan facilitado sus datos podr�n dirigirse a la misma, en su calidad de Responsable del fichero, con el fin de poder ejercitar gratuitamente sus derechos de acceso, rectificaci�n, cancelaci�n y oposici�n respecto de los datos incorporados en sus ficheros. Dado el car�cter confidencial de la informaci�n, Vd. no podr� ejercitar sus derechos telef�nicamente, debido a que este medio no permite acreditar su identidad como titular de los datos registrados. El interesado podr� ejercitar sus derechos mediante comunicaci�n por escrito dirigida a Esri en la siguiente direcci�n: ATT: Responsable LOPD. C/ Emilio Mu�oz 35 \u0096 28037 (Madrid), con la siguiente referencia en su carta: Ejercicio de derechos (junto a su solicitud escrita y firmada, deber� acreditar su personalidad aportando fotocopia de su D.N.I.)."));
        HelpAdapter ca = new HelpAdapter(getActivity(), lista);
        listViewHelp.setAdapter(ca);
    }


}
