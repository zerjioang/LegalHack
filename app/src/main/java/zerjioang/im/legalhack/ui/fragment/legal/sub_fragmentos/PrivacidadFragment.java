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
                "En cumplimiento de lo establecido en la Ley Orgánica 15/1999, de 13 de diciembre, de Protección de Datos de Carácter Personal (en adelante, LOPD), Esri le informa de que los datos de carácter personal por Vd. proporcionados, así como aquellos datos a los que se pueda acceder como consecuencia de su navegación, serán incorporados a los ficheros automatizados de la entidad, pudiendo ejercitar su derecho de acceso, rectificación, cancelación y oposición al tratamiento de sus datos en los términos y condiciones previstos más abajo, en el apartado 2º. En cumplimiento de la Ley 34/2002, de 11 de julio, de Servicios de la Sociedad de la Información y de Comercio Electrónico, (en adelante LSSICE), Esri no enviará por correo electrónico comunicaciones publicitarias que no hayan sido autorizadas por los usuarios. Esri identificará mediante la palabra publicidad aquellos correos o Newsletters que contengan comunicaciones comerciales o promociones, exclusivamente.\n" +
                        "En aquellos casos donde sea necesario cumplimentar un formulario (inscripción a seminarios o eventos), o remitir un correo electrónico (contacta: informacion@esri.es Empleo: rrhh@esri.es) y hacer click en el botón de enviar, su realización implicará necesariamente que haya sido informado (en virtud del artículo 5 LOPD) y, en su caso, ha otorgado el correspondiente consentimiento (a tenor del artículo 6 LOPD) al contenido de la presente política de privacidad.\n" +
                        "\n" +
                        "Por otra parte, mediante el registro de los datos, el usuario otorga su consentimiento al tratamiento de los mismos con las finalidades en cada caso señaladas. Asimismo, el usuario autoriza la cesión de los datos recabados al resto de empresas de EPTISA al que Esri pertenece con fines publicitarios o comerciales.\n" +
                        "\n" +
                        "Así, siempre que ESri le solicite datos de carácter personal, incluirá el correspondiente clausulado legal y un enlace (link) vinculado a la presente Política con el propósito de hacerle partícipe de sus derechos y obligaciones establecidos en la LOPD y LSSIC."));
        lista.add(new HelpItemList(
                "EJERCICIO DE DERECHOS",
                "Aquellas personas físicas que hayan facilitado sus datos podrán dirigirse a la misma, en su calidad de Responsable del fichero, con el fin de poder ejercitar gratuitamente sus derechos de acceso, rectificación, cancelación y oposición respecto de los datos incorporados en sus ficheros. Dado el carácter confidencial de la información, Vd. no podrá ejercitar sus derechos telefónicamente, debido a que este medio no permite acreditar su identidad como titular de los datos registrados. El interesado podrá ejercitar sus derechos mediante comunicación por escrito dirigida a Esri en la siguiente dirección: ATT: Responsable LOPD. C/ Emilio Muñoz 35 \u0096 28037 (Madrid), con la siguiente referencia en su carta: Ejercicio de derechos (junto a su solicitud escrita y firmada, deberá acreditar su personalidad aportando fotocopia de su D.N.I.)."));
        HelpAdapter ca = new HelpAdapter(getActivity(), lista);
        listViewHelp.setAdapter(ca);
    }


}
