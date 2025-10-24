package com.career_guidance.service;

import com.career_guidance.model.Career;
import com.career_guidance.repository.CareerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

@Service
public class CareerService {

    @Autowired
    private CareerRepository careerRepository;

    public List<Career> getAllCareers() {
        // Always re-initialize to ensure all 35 careers with tags are loaded
        careerRepository.deleteAll();
            initializeCareers();
        return careerRepository.findAll();
    }

    public Career getCareerById(Long id) {
        return careerRepository.findById(id).orElse(null);
    }

    public Career saveCareer(Career career) {
        return careerRepository.save(career);
    }

    public void reinitializeCareers() {
        careerRepository.deleteAll();
        initializeCareers();
    }

    private void initializeCareers() {
        // Comprehensive career data with tags
        Career[] careers = new Career[35];

        // Technology & Programming
        careers[0] = Career.builder()
            .title("Software Developer")
            .description("Design, develop, and maintain software applications and systems.")
            .salaryRange("$60,000 - $120,000")
            .educationRequired("Bachelor's in Computer Science")
            .skillsRequired("Programming, Problem-solving, Communication")
            .tags("tech,programming,java,python,software,development,coding,computer")
            .build();

        careers[1] = Career.builder()
            .title("Data Scientist")
            .description("Analyze and interpret complex data to help organizations make decisions.")
            .salaryRange("$80,000 - $150,000")
            .educationRequired("Master's in Data Science/Statistics")
            .skillsRequired("Python, R, Machine Learning, Statistics")
            .tags("tech,data,analytics,python,r,machine learning,statistics,numbers")
            .build();

        careers[2] = Career.builder()
            .title("Web Developer")
            .description("Build and maintain websites and web applications.")
            .salaryRange("$50,000 - $100,000")
            .educationRequired("Bachelor's in Computer Science or Bootcamp")
            .skillsRequired("HTML, CSS, JavaScript, React, Node.js")
            .tags("tech,programming,web,html,css,javascript,frontend,backend")
            .build();

        careers[3] = Career.builder()
            .title("DevOps Engineer")
            .description("Bridge development and operations teams to improve software delivery.")
            .salaryRange("$70,000 - $130,000")
            .educationRequired("Bachelor's in Computer Science")
            .skillsRequired("Linux, Docker, Kubernetes, AWS, CI/CD")
            .tags("tech,devops,cloud,infrastructure,automation,docker,kubernetes")
            .build();

        // Convert all remaining careers to use builder pattern
        for (int i = 4; i < careers.length; i++) {
            careers[i] = Career.builder().build();
        }
        
        careers[4] = Career.builder()
            .title("Cybersecurity Analyst")
            .description("Protect organizations from cyber threats and security breaches.")
            .salaryRange("$60,000 - $120,000")
            .educationRequired("Bachelor's in Cybersecurity or IT")
            .skillsRequired("Network Security, Ethical Hacking, Risk Assessment")
            .tags("tech,security,cybersecurity,protection,networking,hacking")
            .build();

        careers[5] = Career.builder()
            .title("AI/ML Engineer")
            .description("Develop and implement artificial intelligence and machine learning solutions.")
            .salaryRange("$90,000 - $160,000")
            .educationRequired("Master's in AI/ML or Computer Science")
            .skillsRequired("Python, TensorFlow, PyTorch, Neural Networks")
            .tags("tech,ai,machine learning,artificial intelligence,python,algorithms")
            .build();

        // Creative & Design
        careers[6] = Career.builder()
            .title("Graphic Designer")
            .description("Create visual concepts to communicate ideas that inspire and inform.")
            .salaryRange("$40,000 - $80,000")
            .educationRequired("Bachelor's in Graphic Design")
            .skillsRequired("Adobe Creative Suite, Creativity, Typography")
            .tags("creative,design,graphics,visual,art,adobe,photoshop,illustration")
            .build();

        careers[7] = Career.builder()
            .title("UI/UX Designer")
            .description("Design user interfaces and experiences for digital products.")
            .salaryRange("$55,000 - $110,000")
            .educationRequired("Bachelor's in Design or HCI")
            .skillsRequired("Figma, Sketch, User Research, Prototyping")
            .tags("creative,design,ui,ux,user experience,interface,digital,prototyping")
            .build();

        careers[8] = Career.builder()
            .title("Video Editor")
            .description("Edit and produce video content for various media platforms.")
            .salaryRange("$35,000 - $75,000")
            .educationRequired("Bachelor's in Film or Media")
            .skillsRequired("Adobe Premiere, After Effects, Video Production")
            .tags("creative,video,editing,film,media,production,adobe,storytelling")
            .build();

        careers[9] = Career.builder()
            .title("Marketing Designer")
            .description("Create visual marketing materials and brand assets.")
            .salaryRange("$45,000 - $85,000")
            .educationRequired("Bachelor's in Marketing or Design")
            .skillsRequired("Adobe Creative Suite, Branding, Marketing")
            .tags("creative,design,marketing,branding,advertising,visual,communication")
            .build();

        // Business & Finance
        careers[10] = Career.builder()
            .title("Financial Analyst")
            .description("Provide guidance to businesses and individuals making investment decisions.")
            .salaryRange("$50,000 - $100,000")
            .educationRequired("Bachelor's in Finance/Accounting")
            .skillsRequired("Financial Modeling, Excel, Analytical Skills")
            .tags("business,finance,analysis,numbers,excel,investments,financial modeling")
            .build();

        careers[11] = Career.builder()
            .title("Business Analyst")
            .description("Analyze business processes and recommend improvements.")
            .salaryRange("$55,000 - $105,000")
            .educationRequired("Bachelor's in Business Administration")
            .skillsRequired("Data Analysis, Process Improvement, Communication")
            .tags("business,analysis,process improvement,data,strategy,consulting")
            .build();

        careers[12] = Career.builder()
            .title("Project Manager")
            .description("Plan and oversee projects from conception to completion.")
            .salaryRange("$60,000 - $120,000")
            .educationRequired("Bachelor's in Business or PMP Certification")
            .skillsRequired("Leadership, Organization, Communication, Risk Management")
            .tags("business,management,leadership,project,organization,planning")
            .build();

        careers[13] = Career.builder()
            .title("Marketing Manager")
            .description("Develop and implement marketing strategies to promote products or services.")
            .salaryRange("$50,000 - $110,000")
            .educationRequired("Bachelor's in Marketing or Business")
            .skillsRequired("Digital Marketing, Analytics, Campaign Management")
            .tags("business,marketing,advertising,digital,strategy,communication")
            .build();

        careers[14] = Career.builder()
            .title("Sales Representative")
            .description("Sell products or services to customers and build relationships.")
            .salaryRange("$35,000 - $80,000 + Commission")
            .educationRequired("Bachelor's in Business or Sales")
            .skillsRequired("Communication, Negotiation, Customer Service")
            .tags("business,sales,communication,customer service,relationships,persuasion")
            .build();

        // Healthcare & Science
        careers[15] = Career.builder()
            .title("Registered Nurse")
            .description("Provide patient care and support in healthcare settings.")
            .salaryRange("$50,000 - $90,000")
            .educationRequired("Bachelor's in Nursing")
            .skillsRequired("Patient Care, Medical Knowledge, Compassion")
            .tags("healthcare,medical,nursing,patient care,compassion,health")
            .build();

        careers[16] = Career.builder()
            .title("Data Analyst")
            .description("Collect, process, and analyze data to help organizations make decisions.")
            .salaryRange("$45,000 - $85,000")
            .educationRequired("Bachelor's in Statistics or Mathematics")
            .skillsRequired("SQL, Excel, Statistics, Data Visualization")
            .tags("data,analytics,numbers,statistics,sql,excel,research")
            .build();

        careers[17] = Career.builder()
            .title("Research Scientist")
            .description("Conduct research in various scientific fields to advance knowledge.")
            .salaryRange("$60,000 - $120,000")
            .educationRequired("PhD in relevant field")
            .skillsRequired("Research Methods, Analysis, Critical Thinking")
            .tags("science,research,analysis,experiments,laboratory,discovery")
            .build();

        careers[18] = Career.builder()
            .title("Environmental Scientist")
            .description("Study environmental problems and develop solutions.")
            .salaryRange("$45,000 - $95,000")
            .educationRequired("Bachelor's in Environmental Science")
            .skillsRequired("Field Research, Data Analysis, Environmental Knowledge")
            .tags("science,environment,research,field work,nature,conservation")
            .build();

        careers[19] = Career.builder()
            .title("Pharmacist")
            .description("Dispense medications and provide pharmaceutical care.")
            .salaryRange("$90,000 - $150,000")
            .educationRequired("Doctor of Pharmacy")
            .skillsRequired("Medical Knowledge, Attention to Detail, Communication")
            .tags("healthcare,medical,pharmacy,medication,patient care,health")
            .build();

        // Education & Communication
        careers[20] = Career.builder()
            .title("Teacher")
            .description("Educate students in various subjects and grade levels.")
            .salaryRange("$35,000 - $70,000")
            .educationRequired("Bachelor's in Education")
            .skillsRequired("Communication, Patience, Subject Knowledge")
            .tags("education,teaching,communication,patience,learning,students")
            .build();

        careers[21] = Career.builder()
            .title("Technical Writer")
            .description("Create technical documentation and instructional materials.")
            .salaryRange("$45,000 - $85,000")
            .educationRequired("Bachelor's in English or Technical Writing")
            .skillsRequired("Writing, Technical Knowledge, Communication")
            .tags("writing,technical,communication,documentation,instructional,clear")
            .build();

        careers[22] = Career.builder()
            .title("Content Creator")
            .description("Create engaging content for digital platforms and social media.")
            .salaryRange("$30,000 - $80,000")
            .educationRequired("Bachelor's in Communications or Media")
            .skillsRequired("Writing, Social Media, Creativity, Storytelling")
            .tags("creative,writing,content,social media,digital,storytelling,communication")
            .build();

        careers[23] = Career.builder()
            .title("Public Relations Specialist")
            .description("Manage public image and communications for organizations.")
            .salaryRange("$40,000 - $80,000")
            .educationRequired("Bachelor's in PR or Communications")
            .skillsRequired("Communication, Media Relations, Crisis Management")
            .tags("communication,public relations,media,networking,strategy,reputation")
            .build();

        careers[24] = Career.builder()
            .title("Translator")
            .description("Convert written materials from one language to another.")
            .salaryRange("$35,000 - $70,000")
            .educationRequired("Bachelor's in Languages or Linguistics")
            .skillsRequired("Bilingual, Cultural Knowledge, Attention to Detail")
            .tags("language,translation,communication,cultural,bilingual,writing")
            .build();

        // Engineering & Construction
        careers[25] = Career.builder()
            .title("Civil Engineer")
            .description("Design and oversee construction of infrastructure projects.")
            .salaryRange("$60,000 - $120,000")
            .educationRequired("Bachelor's in Civil Engineering")
            .skillsRequired("Mathematics, CAD, Project Management")
            .tags("engineering,construction,infrastructure,math,cad,design,building")
            .build();

        careers[26] = Career.builder()
            .title("Mechanical Engineer")
            .description("Design and develop mechanical systems and devices.")
            .salaryRange("$65,000 - $125,000")
            .educationRequired("Bachelor's in Mechanical Engineering")
            .skillsRequired("Mathematics, CAD, Problem-solving")
            .tags("engineering,mechanical,design,math,cad,problem solving,manufacturing")
            .build();

        careers[27] = Career.builder()
            .title("Architect")
            .description("Design buildings and oversee their construction.")
            .salaryRange("$50,000 - $100,000")
            .educationRequired("Bachelor's in Architecture")
            .skillsRequired("Design, CAD, Mathematics, Creativity")
            .tags("creative,design,architecture,building,construction,cad,artistic")
            .build();

        careers[28] = Career.builder()
            .title("Construction Manager")
            .description("Oversee construction projects from planning to completion.")
            .salaryRange("$55,000 - $110,000")
            .educationRequired("Bachelor's in Construction Management")
            .skillsRequired("Leadership, Project Management, Safety")
            .tags("management,construction,leadership,project,planning,building")
            .build();

        careers[29] = Career.builder()
            .title("Quality Assurance Engineer")
            .description("Test software and systems to ensure quality and functionality.")
            .salaryRange("$55,000 - $105,000")
            .educationRequired("Bachelor's in Computer Science or Engineering")
            .skillsRequired("Testing, Programming, Attention to Detail")
            .tags("tech,testing,quality,software,programming,attention to detail")
            .build();

        // Legal & Government
        careers[30] = Career.builder()
            .title("Lawyer")
            .description("Provide legal advice and represent clients in court.")
            .salaryRange("$60,000 - $200,000+")
            .educationRequired("Juris Doctor (JD)")
            .skillsRequired("Legal Research, Communication, Critical Thinking")
            .tags("legal,law,research,communication,court,justice,advocacy")
            .build();

        careers[31] = Career.builder()
            .title("Paralegal")
            .description("Assist lawyers with legal research and documentation.")
            .salaryRange("$35,000 - $70,000")
            .educationRequired("Associate's in Paralegal Studies")
            .skillsRequired("Legal Research, Organization, Attention to Detail")
            .tags("legal,research,organization,detail oriented,documentation,support")
            .build();

        careers[32] = Career.builder()
            .title("Government Administrator")
            .description("Manage government programs and services.")
            .salaryRange("$45,000 - $90,000")
            .educationRequired("Bachelor's in Public Administration")
            .skillsRequired("Management, Policy Analysis, Communication")
            .tags("government,administration,policy,management,public service,leadership")
            .build();

        careers[33] = Career.builder()
            .title("Social Worker")
            .description("Help individuals and families cope with challenges and improve their lives.")
            .salaryRange("$35,000 - $70,000")
            .educationRequired("Bachelor's in Social Work")
            .skillsRequired("Empathy, Communication, Problem-solving")
            .tags("social work,helping,empathy,communication,community,compassion")
            .build();

        careers[34] = Career.builder()
            .title("Human Resources Specialist")
            .description("Manage employee relations and organizational policies.")
            .salaryRange("$40,000 - $80,000")
            .educationRequired("Bachelor's in HR or Business")
            .skillsRequired("Communication, Conflict Resolution, Organization")
            .tags("business,human resources,communication,employee relations,management")
            .build();

        careerRepository.saveAll(Arrays.asList(careers));
    }
}