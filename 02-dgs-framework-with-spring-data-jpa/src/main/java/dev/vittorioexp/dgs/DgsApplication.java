package dev.vittorioexp.dgs;

import dev.vittorioexp.dgs.model.Agency;
import dev.vittorioexp.dgs.model.Agent;
import dev.vittorioexp.dgs.model.Property;
import dev.vittorioexp.dgs.model.PropertyType;
import dev.vittorioexp.dgs.repository.AgencyRepository;
import dev.vittorioexp.dgs.repository.AgentRepository;
import dev.vittorioexp.dgs.repository.PropertyRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.List;

@SpringBootApplication
public class DgsApplication {

    public static void main(String[] args) {
        SpringApplication.run(DgsApplication.class, args);
    }

    @Bean
    CommandLineRunner commandLineRunner(
            AgencyRepository agencyRepository,
            AgentRepository agentRepository,
            PropertyRepository propertyRepository
    ) {
        return args -> {

            agencyRepository.saveAll(List.of(
                    Agency
                            .builder()
                            .name("Dream Estates")
                            .taxCode("3LURH283R7F3F8IFUHJ394")
                            .build(),
                    Agency
                            .builder()
                            .name("Private properties DALLAS")
                            .taxCode("LKFHWIEURH8347R3R")
                            .build(),
                    Agency
                            .builder()
                            .name("Abra buildings")
                            .taxCode("R8327FH287WFHD298F")
                            .build(),
                    Agency
                            .builder()
                            .name("Acme")
                            .taxCode("3RIUE8UH3587G385GU")
                            .build(),
                    Agency
                            .builder()
                            .name("MS land")
                            .taxCode("F34UFH387HF8374")
                            .build()
            ));


            agentRepository.saveAll(List.of(
                    Agent
                            .builder()
                            .fullName("Ada Smith")
                            .agency(agencyRepository.findById(1).orElse(null))
                            .build(),
                    Agent
                            .builder()
                            .fullName("Bob Muller")
                            .agency(agencyRepository.findById(1).orElse(null))
                            .build(),
                    Agent
                            .builder()
                            .fullName("Adam James")
                            .agency(agencyRepository.findById(1).orElse(null))
                            .build(),
                    Agent
                            .builder()
                            .fullName("William james")
                            .agency(agencyRepository.findById(1).orElse(null))
                            .build(),
                    Agent
                            .builder()
                            .fullName("Gregorio Roberts")
                            .agency(agencyRepository.findById(2).orElse(null))
                            .build(),
                    Agent
                            .builder()
                            .fullName("AGENT20389")
                            .agency(agencyRepository.findById(3).orElse(null))
                            .build(),
                    Agent
                            .builder()
                            .fullName("AGENT93485")
                            .agency(agencyRepository.findById(3).orElse(null))
                            .build(),
                    Agent
                            .builder()
                            .fullName("AGENT102938")
                            .agency(agencyRepository.findById(4).orElse(null))
                            .build(),
                    Agent
                            .builder()
                            .fullName("AGENT43687")
                            .agency(agencyRepository.findById(5).orElse(null))
                            .build(),
                    Agent
                            .builder()
                            .fullName("AGENT00113")
                            .agency(agencyRepository.findById(5).orElse(null))
                            .build()

            ));

            propertyRepository.saveAll(List.of(
                    Property
                            .builder()
                            .name("ERTY45Y")
                            .type(PropertyType.AGRICULTURAL_LAND)
                            .agency(agencyRepository.findById(1).orElse(null))
                            .build(),
                    Property
                            .builder()
                            .name("Y454E5Y4")
                            .type(PropertyType.AGRICULTURAL_LAND)
                            .agency(agencyRepository.findById(1).orElse(null))
                            .build(),
                    Property
                            .builder()
                            .name("Y454Y")
                            .type(PropertyType.AGRICULTURAL_LAND)
                            .agency(agencyRepository.findById(1).orElse(null))
                            .build(),
                    Property
                            .builder()
                            .name("RY5R6Y5")
                            .type(PropertyType.AGRICULTURAL_LAND)
                            .agency(agencyRepository.findById(1).orElse(null))
                            .build(),
                    Property
                            .builder()
                            .name("RTYR5Y54")
                            .type(PropertyType.BUILDING_LAND)
                            .agency(agencyRepository.findById(1).orElse(null))
                            .build(),
                    Property
                            .builder()
                            .name("5IU6I")
                            .type(PropertyType.BUILDING_LAND)
                            .agency(agencyRepository.findById(1).orElse(null))
                            .build(),
                    Property
                            .builder()
                            .name("6IT7T6")
                            .type(PropertyType.WOODED_LAND)
                            .agency(agencyRepository.findById(1).orElse(null))
                            .build(),
                    Property
                            .builder()
                            .name("R3R2R2")
                            .type(PropertyType.WOODED_LAND)
                            .agency(agencyRepository.findById(1).orElse(null))
                            .build(),
                    Property
                            .builder()
                            .name("R23Q23")
                            .type(PropertyType.AGRICULTURAL_LAND)
                            .agency(agencyRepository.findById(2).orElse(null))
                            .build(),
                    Property
                            .builder()
                            .name("1EE21E2")
                            .type(PropertyType.AGRICULTURAL_LAND)
                            .agency(agencyRepository.findById(2).orElse(null))
                            .build(),
                    Property
                            .builder()
                            .name("E121E21")
                            .type(PropertyType.AGRICULTURAL_LAND)
                            .agency(agencyRepository.findById(3).orElse(null))
                            .build(),
                    Property
                            .builder()
                            .name("12E12E12")
                            .type(PropertyType.AGRICULTURAL_LAND)
                            .agency(agencyRepository.findById(3).orElse(null))
                            .build(),
                    Property
                            .builder()
                            .name("R2323RQ3W")
                            .type(PropertyType.AGRICULTURAL_LAND)
                            .agency(agencyRepository.findById(3).orElse(null))
                            .build(),
                    Property
                            .builder()
                            .name("T3WF4TGF3E")
                            .type(PropertyType.AGRICULTURAL_LAND)
                            .agency(agencyRepository.findById(3).orElse(null))
                            .build(),
                    Property
                            .builder()
                            .name("T3W4EGTWG4REW")
                            .type(PropertyType.BUILDING_LAND)
                            .agency(agencyRepository.findById(4).orElse(null))
                            .build(),
                    Property
                            .builder()
                            .name("ET43T4E")
                            .type(PropertyType.BUILDING_LAND)
                            .agency(agencyRepository.findById(4).orElse(null))
                            .build(),
                    Property
                            .builder()
                            .name("5YYY5")
                            .type(PropertyType.WOODED_LAND)
                            .agency(agencyRepository.findById(4).orElse(null))
                            .build(),
                    Property
                            .builder()
                            .name("U6UT5R6U")
                            .type(PropertyType.WOODED_LAND)
                            .agency(agencyRepository.findById(4).orElse(null))
                            .build(),
                    Property
                            .builder()
                            .name("R5T6U5R6")
                            .type(PropertyType.AGRICULTURAL_LAND)
                            .agency(agencyRepository.findById(5).orElse(null))
                            .build(),
                    Property
                            .builder()
                            .name("5T6U6T5U")
                            .type(PropertyType.AGRICULTURAL_LAND)
                            .agency(agencyRepository.findById(5).orElse(null))
                            .build(),
                    Property
                            .builder()
                            .name("A32001")
                            .type(PropertyType.AGRICULTURAL_LAND)
                            .agency(agencyRepository.findById(1).orElse(null))
                            .build(),
                    Property
                            .builder()
                            .name("A32002")
                            .type(PropertyType.AGRICULTURAL_LAND)
                            .agency(agencyRepository.findById(1).orElse(null))
                            .build(),
                    Property
                            .builder()
                            .name("A32003")
                            .type(PropertyType.AGRICULTURAL_LAND)
                            .agency(agencyRepository.findById(1).orElse(null))
                            .build(),
                    Property
                            .builder()
                            .name("A32004")
                            .type(PropertyType.AGRICULTURAL_LAND)
                            .agency(agencyRepository.findById(1).orElse(null))
                            .build(),
                    Property
                            .builder()
                            .name("B5001")
                            .type(PropertyType.BUILDING_LAND)
                            .agency(agencyRepository.findById(1).orElse(null))
                            .build(),
                    Property
                            .builder()
                            .name("B5002")
                            .type(PropertyType.BUILDING_LAND)
                            .agency(agencyRepository.findById(1).orElse(null))
                            .build(),
                    Property
                            .builder()
                            .name("W001")
                            .type(PropertyType.WOODED_LAND)
                            .agency(agencyRepository.findById(1).orElse(null))
                            .build(),
                    Property
                            .builder()
                            .name("W002")
                            .type(PropertyType.WOODED_LAND)
                            .agency(agencyRepository.findById(1).orElse(null))
                            .build(),
                    Property
                            .builder()
                            .name("Agric-01")
                            .type(PropertyType.AGRICULTURAL_LAND)
                            .agency(agencyRepository.findById(2).orElse(null))
                            .build(),
                    Property
                            .builder()
                            .name("Agric-02")
                            .type(PropertyType.AGRICULTURAL_LAND)
                            .agency(agencyRepository.findById(2).orElse(null))
                            .build(),
                    Property
                            .builder()
                            .name("5667u4567")
                            .type(PropertyType.AGRICULTURAL_LAND)
                            .agency(agencyRepository.findById(3).orElse(null))
                            .build(),
                    Property
                            .builder()
                            .name("35t3e5tg")
                            .type(PropertyType.AGRICULTURAL_LAND)
                            .agency(agencyRepository.findById(3).orElse(null))
                            .build(),
                    Property
                            .builder()
                            .name("35tgey")
                            .type(PropertyType.AGRICULTURAL_LAND)
                            .agency(agencyRepository.findById(3).orElse(null))
                            .build(),
                    Property
                            .builder()
                            .name("4hy4yh4")
                            .type(PropertyType.AGRICULTURAL_LAND)
                            .agency(agencyRepository.findById(3).orElse(null))
                            .build(),
                    Property
                            .builder()
                            .name("24r3w4t")
                            .type(PropertyType.BUILDING_LAND)
                            .agency(agencyRepository.findById(4).orElse(null))
                            .build(),
                    Property
                            .builder()
                            .name("9876t3t")
                            .type(PropertyType.BUILDING_LAND)
                            .agency(agencyRepository.findById(4).orElse(null))
                            .build(),
                    Property
                            .builder()
                            .name("wfqw442")
                            .type(PropertyType.WOODED_LAND)
                            .agency(agencyRepository.findById(4).orElse(null))
                            .build(),
                    Property
                            .builder()
                            .name("aerge5t")
                            .type(PropertyType.WOODED_LAND)
                            .agency(agencyRepository.findById(4).orElse(null))
                            .build(),
                    Property
                            .builder()
                            .name("34tg3w54yg")
                            .type(PropertyType.AGRICULTURAL_LAND)
                            .agency(agencyRepository.findById(5).orElse(null))
                            .build(),
                    Property
                            .builder()
                            .name("q3rr2q3r2w")
                            .type(PropertyType.AGRICULTURAL_LAND)
                            .agency(agencyRepository.findById(5).orElse(null))
                            .build()

            ));
        };
    }
}
