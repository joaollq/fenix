/*
 * 
 * Created on 2003/08/15
 */

package ServidorAplicacao.Servico.sop;

/**
 * @author Luis Cruz & Sara Ribeiro
 */
import java.util.List;

import Dominio.Aula;
import Dominio.IAula;
import Dominio.ITurnoAula;
import ServidorAplicacao.IServico;
import ServidorAplicacao.Servico.exceptions.FenixServiceException;
import ServidorPersistente.ExcepcaoPersistencia;
import ServidorPersistente.ISuportePersistente;
import ServidorPersistente.ITurnoAulaPersistente;
import ServidorPersistente.OJB.SuportePersistenteOJB;

public class DeleteLessons implements IServico {

    private static DeleteLessons _servico = new DeleteLessons();

    /**
     * The singleton access method of this class.
     */
    public static DeleteLessons getService() {
        return _servico;
    }

    /**
     * The actor of this class.
     */
    private DeleteLessons() {
    }

    /**
     * Devolve o nome do servico
     */
    public final String getNome() {
        return "DeleteLessons";
    }

    public Object run(List lessonOIDs) throws FenixServiceException {

        boolean result = false;

        try {

            ISuportePersistente sp = SuportePersistenteOJB.getInstance();

            for (int j = 0; j < lessonOIDs.size(); j++) {
                IAula lesson = (IAula) sp.getIAulaPersistente().readByOID(
                        Aula.class, (Integer) lessonOIDs.get(j));

                if (lesson != null) {
                    ITurnoAulaPersistente persistentShiftLesson = sp
                            .getITurnoAulaPersistente();

                    List shiftLessonList = persistentShiftLesson
                            .readByLesson(lesson);
                    for (int i = 0; i < shiftLessonList.size(); i++) {
                        persistentShiftLesson
                                .delete((ITurnoAula) shiftLessonList.get(i));
                    }
                    
                    sp.getIAulaPersistente().delete(lesson);
                }
            }

            result = true;
        } catch (ExcepcaoPersistencia ex) {
            throw new FenixServiceException("Error deleting lesson");
        }

        return new Boolean(result);

    }

}