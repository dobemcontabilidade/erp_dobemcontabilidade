import { TestBed } from '@angular/core/testing';
import { provideHttpClientTesting, HttpTestingController } from '@angular/common/http/testing';
import { provideHttpClient } from '@angular/common/http';

import { ICertificadoDigital } from '../certificado-digital.model';
import { sampleWithRequiredData, sampleWithNewData, sampleWithPartialData, sampleWithFullData } from '../certificado-digital.test-samples';

import { CertificadoDigitalService, RestCertificadoDigital } from './certificado-digital.service';

const requireRestSample: RestCertificadoDigital = {
  ...sampleWithRequiredData,
  dataContratacao: sampleWithRequiredData.dataContratacao?.toJSON(),
};

describe('CertificadoDigital Service', () => {
  let service: CertificadoDigitalService;
  let httpMock: HttpTestingController;
  let expectedResult: ICertificadoDigital | ICertificadoDigital[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [provideHttpClient(), provideHttpClientTesting()],
    });
    expectedResult = null;
    service = TestBed.inject(CertificadoDigitalService);
    httpMock = TestBed.inject(HttpTestingController);
  });

  describe('Service methods', () => {
    it('should find an element', () => {
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.find(123).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should create a CertificadoDigital', () => {
      const certificadoDigital = { ...sampleWithNewData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.create(certificadoDigital).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a CertificadoDigital', () => {
      const certificadoDigital = { ...sampleWithRequiredData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.update(certificadoDigital).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a CertificadoDigital', () => {
      const patchObject = { ...sampleWithPartialData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of CertificadoDigital', () => {
      const returnedFromService = { ...requireRestSample };

      const expected = { ...sampleWithRequiredData };

      service.query().subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush([returnedFromService]);
      httpMock.verify();
      expect(expectedResult).toMatchObject([expected]);
    });

    it('should delete a CertificadoDigital', () => {
      const expected = true;

      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult).toBe(expected);
    });

    describe('addCertificadoDigitalToCollectionIfMissing', () => {
      it('should add a CertificadoDigital to an empty array', () => {
        const certificadoDigital: ICertificadoDigital = sampleWithRequiredData;
        expectedResult = service.addCertificadoDigitalToCollectionIfMissing([], certificadoDigital);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(certificadoDigital);
      });

      it('should not add a CertificadoDigital to an array that contains it', () => {
        const certificadoDigital: ICertificadoDigital = sampleWithRequiredData;
        const certificadoDigitalCollection: ICertificadoDigital[] = [
          {
            ...certificadoDigital,
          },
          sampleWithPartialData,
        ];
        expectedResult = service.addCertificadoDigitalToCollectionIfMissing(certificadoDigitalCollection, certificadoDigital);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a CertificadoDigital to an array that doesn't contain it", () => {
        const certificadoDigital: ICertificadoDigital = sampleWithRequiredData;
        const certificadoDigitalCollection: ICertificadoDigital[] = [sampleWithPartialData];
        expectedResult = service.addCertificadoDigitalToCollectionIfMissing(certificadoDigitalCollection, certificadoDigital);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(certificadoDigital);
      });

      it('should add only unique CertificadoDigital to an array', () => {
        const certificadoDigitalArray: ICertificadoDigital[] = [sampleWithRequiredData, sampleWithPartialData, sampleWithFullData];
        const certificadoDigitalCollection: ICertificadoDigital[] = [sampleWithRequiredData];
        expectedResult = service.addCertificadoDigitalToCollectionIfMissing(certificadoDigitalCollection, ...certificadoDigitalArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const certificadoDigital: ICertificadoDigital = sampleWithRequiredData;
        const certificadoDigital2: ICertificadoDigital = sampleWithPartialData;
        expectedResult = service.addCertificadoDigitalToCollectionIfMissing([], certificadoDigital, certificadoDigital2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(certificadoDigital);
        expect(expectedResult).toContain(certificadoDigital2);
      });

      it('should accept null and undefined values', () => {
        const certificadoDigital: ICertificadoDigital = sampleWithRequiredData;
        expectedResult = service.addCertificadoDigitalToCollectionIfMissing([], null, certificadoDigital, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(certificadoDigital);
      });

      it('should return initial array if no CertificadoDigital is added', () => {
        const certificadoDigitalCollection: ICertificadoDigital[] = [sampleWithRequiredData];
        expectedResult = service.addCertificadoDigitalToCollectionIfMissing(certificadoDigitalCollection, undefined, null);
        expect(expectedResult).toEqual(certificadoDigitalCollection);
      });
    });

    describe('compareCertificadoDigital', () => {
      it('Should return true if both entities are null', () => {
        const entity1 = null;
        const entity2 = null;

        const compareResult = service.compareCertificadoDigital(entity1, entity2);

        expect(compareResult).toEqual(true);
      });

      it('Should return false if one entity is null', () => {
        const entity1 = { id: 123 };
        const entity2 = null;

        const compareResult1 = service.compareCertificadoDigital(entity1, entity2);
        const compareResult2 = service.compareCertificadoDigital(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey differs', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 456 };

        const compareResult1 = service.compareCertificadoDigital(entity1, entity2);
        const compareResult2 = service.compareCertificadoDigital(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey matches', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 123 };

        const compareResult1 = service.compareCertificadoDigital(entity1, entity2);
        const compareResult2 = service.compareCertificadoDigital(entity2, entity1);

        expect(compareResult1).toEqual(true);
        expect(compareResult2).toEqual(true);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
