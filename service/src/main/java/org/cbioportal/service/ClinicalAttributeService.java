package org.cbioportal.service;

import java.util.List;
import org.cbioportal.model.ClinicalAttribute;
import org.cbioportal.model.ClinicalAttributeCount;
import org.cbioportal.model.meta.BaseMeta;
import org.cbioportal.service.exception.ClinicalAttributeNotFoundException;
import org.cbioportal.service.exception.StudyNotFoundException;

public interface ClinicalAttributeService {
    List<ClinicalAttribute> getAllClinicalAttributes(
        String projection,
        Integer pageSize,
        Integer pageNumber,
        String sortBy,
        String direction
    );

    BaseMeta getMetaClinicalAttributes();

    ClinicalAttribute getClinicalAttribute(
        String studyId,
        String clinicalAttributeId
    )
        throws ClinicalAttributeNotFoundException, StudyNotFoundException;

    List<ClinicalAttribute> getAllClinicalAttributesInStudy(
        String studyId,
        String projection,
        Integer pageSize,
        Integer pageNumber,
        String sortBy,
        String direction
    )
        throws StudyNotFoundException;

    BaseMeta getMetaClinicalAttributesInStudy(String studyId)
        throws StudyNotFoundException;

    List<ClinicalAttribute> fetchClinicalAttributes(
        List<String> studyIds,
        String projection
    );

    BaseMeta fetchMetaClinicalAttributes(List<String> studyIds);

    List<ClinicalAttributeCount> getClinicalAttributeCountsBySampleIds(
        List<String> studyIds,
        List<String> sampleIds
    );

    List<ClinicalAttributeCount> getClinicalAttributeCountsBySampleListId(
        String sampleListId
    );

    List<ClinicalAttribute> getClinicalAttributesByStudyIdsAndAttributeIds(
        List<String> studyIds,
        List<String> attributeIds
    );
}
