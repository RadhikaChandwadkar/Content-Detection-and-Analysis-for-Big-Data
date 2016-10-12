import static org.semanticweb.owlapi.search.Searcher.annotations;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import org.semanticweb.owlapi.apibinding.OWLManager;
import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLAnnotation;
import org.semanticweb.owlapi.model.OWLAnnotationProperty;
import org.semanticweb.owlapi.model.OWLClass;
import org.semanticweb.owlapi.model.OWLDataFactory;
import org.semanticweb.owlapi.model.OWLException;
import org.semanticweb.owlapi.model.OWLLiteral;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLOntologyCreationException;
import org.semanticweb.owlapi.model.OWLOntologyManager;
import org.semanticweb.owlapi.vocab.OWLRDFVocabulary;

//this class loads the Ontology from the file or web and parses and prints the OWL classes
public class OwlParser 
{

    private final OWLOntology ontology;
    private OWLDataFactory df;

    public OwlParser(OWLOntology ontology, OWLDataFactory df) 
    {
        this.ontology = ontology;
        this.df = df;
    }

    //parses the ontology object 
    public void parseOntology() throws OWLOntologyCreationException
    {

    	//each ontology in the sweetAll.owl file
    	for(OWLOntology importedOnt:ontology.getImports())
    	{
    		//load the owl class in the imports
    		for (OWLClass cls : importedOnt.getClassesInSignature()) 
    		{
    			//get the name
    			String id = cls.getIRI().toString();
    			//print the name with label
    			String label = get(cls,OWLRDFVocabulary.RDFS_LABEL.getIRI().toString()).get(0);
    			System.out.println("this is :"+ label+ ": [" + id + "]");
    		}
    	}
    }

    //this prints the property of each owl class loaded 
    private List<String> get(OWLClass clazz, String property) {
        List<String> ret = new ArrayList<>();

        // extract the owl property 
        final OWLAnnotationProperty owlProperty = df
                .getOWLAnnotationProperty(IRI.create(property));
        for (OWLOntology o : ontology.getImportsClosure()) {
            for (OWLAnnotation annotation : annotations(
                    o.getAnnotationAssertionAxioms(clazz.getIRI()), owlProperty)) {
                if (annotation.getValue() instanceof OWLLiteral) {
                    OWLLiteral val = (OWLLiteral) annotation.getValue();
                    ret.add(val.getLiteral());
                }
            }
        }
        return ret;
    }

    public static void main(String[] args) throws OWLException,
            InstantiationException, IllegalAccessException,
            ClassNotFoundException {

    	// loading ontology from the localhost drive
        File file = new File("F:/2.3/sweetAll.owl");

        
        // Now load the local copy using OWL manager
        OWLOntologyManager manager = OWLManager.createOWLOntologyManager();
        OWLOntology ontology = manager.loadOntologyFromOntologyDocument(file);

  //      System.out.println("Loaded Finally");
        
        //This below commented code loads the Ontology from Web
        
      /*  IRI documentIRI = IRI.create(x);
     
        OWLOntology ontology = manager.loadOntologyFromOntologyDocument(documentIRI);*/
        OwlParser parser = new OwlParser(ontology, manager.getOWLDataFactory());
        parser.parseOntology();
    }
}
