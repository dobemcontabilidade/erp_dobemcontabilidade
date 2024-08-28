import { TestBed } from '@angular/core/testing';
import { provideHttpClientTesting, HttpTestingController } from '@angular/common/http/testing';
import { provideHttpClient } from '@angular/common/http';

import { ICertificadoDigitalEmpresa } from '../certificado-digital-empresa.model';
import {
  sampleWithRequiredData,
  sampleWithNewData,
  sampleWithPartialData,
  sampleWithFullData,
} from '../certificado-digital-empresa.test-samples';

import { CertificadoDigitalEmpresaService, RestCertificadoDigitalEmpresa } from './certificado-digital-empresa.service';

const requireRestSample: RestCertificadoDigitalEmpresa = {
  ...sampleWithRequiredData,
  dataContratacao: sampleWithRequiredData.dataContratacao?.toJSON(),
  dataVencimento: sampleWithRequiredData.dataVencimento?.toJSON(),
};

describe('CertificadoDigitalEmpresa Service', () => {
  let service: CertificadoDigitalEmpresaService;
  let httpMock: HttpTestingController;
  let expectedResult: ICertificadoDigitalEmpresa | ICertificadoDigitalEmpresa[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [provideHttpClient(), provideHttpClientTesting()],
    });
    expectedResult = null;
    service = TestBed.inject(CertificadoDigitalEmpresaService);
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

    it('should create a CertificadoDigitalEmpresa', () => {
      const certificadoDigitalEmpresa = { ...sampleWithNewData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.create(certificadoDigitalEmpresa).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a CertificadoDigitalEmpresa', () => {
      const certificadoDigitalEmpresa = { ...sampleWithRequiredData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.update(certificadoDigitalEmpresa).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a CertificadoDigitalEmpresa', () => {
      const patchObject = { ...sampleWithPartialData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of CertificadoDigitalEmpresa', () => {
      const returnedFromService = { ...requireRestSample };

      const expected = { ...sampleWithRequiredData };

      service.query().subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush([returnedFromService]);
      httpMock.verify();
      expect(expectedResult).toMatchObject([expected]);
    });

    it('should delete a CertificadoDigitalEmpresa', () => {
      const expected = true;

      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult).toBe(expected);
    });

    describe('addCertificadoDigitalEmpresaToCollectionIfMissing', () => {
      it('should add a CertificadoDigitalEmpresa to an empty array', () => {
        const certificadoDigitalEmpresa: ICertificadoDigitalEmpresa = sampleWithRequiredData;
        expectedResult = service.addCertificadoDigitalEmpresaToCollectionIfMissing([], certificadoDigitalEmpresa);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(certificadoDigitalEmpresa);
      });

      it('should not add a CertificadoDigitalEmpresa to an array that contains it', () => {
        const certificadoDigitalEmpresa: ICertificadoDigitalEmpresa = sampleWithRequiredData;
        const certificadoDigitalEmpresaCollection: ICertificadoDigitalEmpresa[] = [
          {
            ...certificadoDigitalEmpresa,
          },
          sampleWithPartialData,
        ];
        expectedResult = service.addCertificadoDigitalEmpresaToCollectionIfMissing(
          certificadoDigitalEmpresaCollection,
          certificadoDigitalEmpresa,
        );
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a CertificadoDigitalEmpresa to an array that doesn't contain it", () => {
        const certificadoDigitalEmpresa: ICertificadoDigitalEmpresa = sampleWithRequiredData;
        const certificadoDigitalEmpresaCollection: ICertificadoDigitalEmpresa[] = [sampleWithPartialData];
        expectedResult = service.addCertificadoDigitalEmpresaToCollectionIfMissing(
          certificadoDigitalEmpresaCollection,
          certificadoDigitalEmpresa,
        );
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(certificadoDigitalEmpresa);
      });

      it('should add only unique CertificadoDigitalEmpresa to an array', () => {
        const certificadoDigitalEmpresaArray: ICertificadoDigitalEmpresa[] = [
          sampleWithRequiredData,
          sampleWithPartialData,
          sampleWithFullData,
        ];
        const certificadoDigitalEmpresaCollection: ICertificadoDigitalEmpresa[] = [sampleWithRequiredData];
        expectedResult = service.addCertificadoDigitalEmpresaToCollectionIfMissing(
          certificadoDigitalEmpresaCollection,
          ...certificadoDigitalEmpresaArray,
        );
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const certificadoDigitalEmpresa: ICertificadoDigitalEmpresa = sampleWithRequiredData;
        const certificadoDigitalEmpresa2: ICertificadoDigitalEmpresa = sampleWithPartialData;
        expectedResult = service.addCertificadoDigitalEmpresaToCollectionIfMissing(
          [],
          certificadoDigitalEmpresa,
          certificadoDigitalEmpresa2,
        );
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(certificadoDigitalEmpresa);
        expect(expectedResult).toContain(certificadoDigitalEmpresa2);
      });

      it('should accept null and undefined values', () => {
        const certificadoDigitalEmpresa: ICertificadoDigitalEmpresa = sampleWithRequiredData;
        expectedResult = service.addCertificadoDigitalEmpresaToCollectionIfMissing([], null, certificadoDigitalEmpresa, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(certificadoDigitalEmpresa);
      });

      it('should return initial array if no CertificadoDigitalEmpresa is added', () => {
        const certificadoDigitalEmpresaCollection: ICertificadoDigitalEmpresa[] = [sampleWithRequiredData];
        expectedResult = service.addCertificadoDigitalEmpresaToCollectionIfMissing(certificadoDigitalEmpresaCollection, undefined, null);
        expect(expectedResult).toEqual(certificadoDigitalEmpresaCollection);
      });
    });

    describe('compareCertificadoDigitalEmpresa', () => {
      it('Should return true if both entities are null', () => {
        const entity1 = null;
        const entity2 = null;

        const compareResult = service.compareCertificadoDigitalEmpresa(entity1, entity2);

        expect(compareResult).toEqual(true);
      });

      it('Should return false if one entity is null', () => {
        const entity1 = { id: 123 };
        const entity2 = null;

        const compareResult1 = service.compareCertificadoDigitalEmpresa(entity1, entity2);
        const compareResult2 = service.compareCertificadoDigitalEmpresa(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey differs', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 456 };

        const compareResult1 = service.compareCertificadoDigitalEmpresa(entity1, entity2);
        const compareResult2 = service.compareCertificadoDigitalEmpresa(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey matches', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 123 };

        const compareResult1 = service.compareCertificadoDigitalEmpresa(entity1, entity2);
        const compareResult2 = service.compareCertificadoDigitalEmpresa(entity2, entity1);

        expect(compareResult1).toEqual(true);
        expect(compareResult2).toEqual(true);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
