import { TestBed } from '@angular/core/testing';
import { provideHttpClientTesting, HttpTestingController } from '@angular/common/http/testing';
import { provideHttpClient } from '@angular/common/http';

import { IAgenteIntegracaoEstagio } from '../agente-integracao-estagio.model';
import {
  sampleWithRequiredData,
  sampleWithNewData,
  sampleWithPartialData,
  sampleWithFullData,
} from '../agente-integracao-estagio.test-samples';

import { AgenteIntegracaoEstagioService } from './agente-integracao-estagio.service';

const requireRestSample: IAgenteIntegracaoEstagio = {
  ...sampleWithRequiredData,
};

describe('AgenteIntegracaoEstagio Service', () => {
  let service: AgenteIntegracaoEstagioService;
  let httpMock: HttpTestingController;
  let expectedResult: IAgenteIntegracaoEstagio | IAgenteIntegracaoEstagio[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [provideHttpClient(), provideHttpClientTesting()],
    });
    expectedResult = null;
    service = TestBed.inject(AgenteIntegracaoEstagioService);
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

    it('should create a AgenteIntegracaoEstagio', () => {
      const agenteIntegracaoEstagio = { ...sampleWithNewData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.create(agenteIntegracaoEstagio).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a AgenteIntegracaoEstagio', () => {
      const agenteIntegracaoEstagio = { ...sampleWithRequiredData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.update(agenteIntegracaoEstagio).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a AgenteIntegracaoEstagio', () => {
      const patchObject = { ...sampleWithPartialData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of AgenteIntegracaoEstagio', () => {
      const returnedFromService = { ...requireRestSample };

      const expected = { ...sampleWithRequiredData };

      service.query().subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush([returnedFromService]);
      httpMock.verify();
      expect(expectedResult).toMatchObject([expected]);
    });

    it('should delete a AgenteIntegracaoEstagio', () => {
      const expected = true;

      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult).toBe(expected);
    });

    describe('addAgenteIntegracaoEstagioToCollectionIfMissing', () => {
      it('should add a AgenteIntegracaoEstagio to an empty array', () => {
        const agenteIntegracaoEstagio: IAgenteIntegracaoEstagio = sampleWithRequiredData;
        expectedResult = service.addAgenteIntegracaoEstagioToCollectionIfMissing([], agenteIntegracaoEstagio);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(agenteIntegracaoEstagio);
      });

      it('should not add a AgenteIntegracaoEstagio to an array that contains it', () => {
        const agenteIntegracaoEstagio: IAgenteIntegracaoEstagio = sampleWithRequiredData;
        const agenteIntegracaoEstagioCollection: IAgenteIntegracaoEstagio[] = [
          {
            ...agenteIntegracaoEstagio,
          },
          sampleWithPartialData,
        ];
        expectedResult = service.addAgenteIntegracaoEstagioToCollectionIfMissing(
          agenteIntegracaoEstagioCollection,
          agenteIntegracaoEstagio,
        );
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a AgenteIntegracaoEstagio to an array that doesn't contain it", () => {
        const agenteIntegracaoEstagio: IAgenteIntegracaoEstagio = sampleWithRequiredData;
        const agenteIntegracaoEstagioCollection: IAgenteIntegracaoEstagio[] = [sampleWithPartialData];
        expectedResult = service.addAgenteIntegracaoEstagioToCollectionIfMissing(
          agenteIntegracaoEstagioCollection,
          agenteIntegracaoEstagio,
        );
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(agenteIntegracaoEstagio);
      });

      it('should add only unique AgenteIntegracaoEstagio to an array', () => {
        const agenteIntegracaoEstagioArray: IAgenteIntegracaoEstagio[] = [
          sampleWithRequiredData,
          sampleWithPartialData,
          sampleWithFullData,
        ];
        const agenteIntegracaoEstagioCollection: IAgenteIntegracaoEstagio[] = [sampleWithRequiredData];
        expectedResult = service.addAgenteIntegracaoEstagioToCollectionIfMissing(
          agenteIntegracaoEstagioCollection,
          ...agenteIntegracaoEstagioArray,
        );
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const agenteIntegracaoEstagio: IAgenteIntegracaoEstagio = sampleWithRequiredData;
        const agenteIntegracaoEstagio2: IAgenteIntegracaoEstagio = sampleWithPartialData;
        expectedResult = service.addAgenteIntegracaoEstagioToCollectionIfMissing([], agenteIntegracaoEstagio, agenteIntegracaoEstagio2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(agenteIntegracaoEstagio);
        expect(expectedResult).toContain(agenteIntegracaoEstagio2);
      });

      it('should accept null and undefined values', () => {
        const agenteIntegracaoEstagio: IAgenteIntegracaoEstagio = sampleWithRequiredData;
        expectedResult = service.addAgenteIntegracaoEstagioToCollectionIfMissing([], null, agenteIntegracaoEstagio, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(agenteIntegracaoEstagio);
      });

      it('should return initial array if no AgenteIntegracaoEstagio is added', () => {
        const agenteIntegracaoEstagioCollection: IAgenteIntegracaoEstagio[] = [sampleWithRequiredData];
        expectedResult = service.addAgenteIntegracaoEstagioToCollectionIfMissing(agenteIntegracaoEstagioCollection, undefined, null);
        expect(expectedResult).toEqual(agenteIntegracaoEstagioCollection);
      });
    });

    describe('compareAgenteIntegracaoEstagio', () => {
      it('Should return true if both entities are null', () => {
        const entity1 = null;
        const entity2 = null;

        const compareResult = service.compareAgenteIntegracaoEstagio(entity1, entity2);

        expect(compareResult).toEqual(true);
      });

      it('Should return false if one entity is null', () => {
        const entity1 = { id: 123 };
        const entity2 = null;

        const compareResult1 = service.compareAgenteIntegracaoEstagio(entity1, entity2);
        const compareResult2 = service.compareAgenteIntegracaoEstagio(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey differs', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 456 };

        const compareResult1 = service.compareAgenteIntegracaoEstagio(entity1, entity2);
        const compareResult2 = service.compareAgenteIntegracaoEstagio(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey matches', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 123 };

        const compareResult1 = service.compareAgenteIntegracaoEstagio(entity1, entity2);
        const compareResult2 = service.compareAgenteIntegracaoEstagio(entity2, entity1);

        expect(compareResult1).toEqual(true);
        expect(compareResult2).toEqual(true);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
