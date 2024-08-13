import { TestBed } from '@angular/core/testing';
import { provideHttpClientTesting, HttpTestingController } from '@angular/common/http/testing';
import { provideHttpClient } from '@angular/common/http';

import { ICompetencia } from '../competencia.model';
import { sampleWithRequiredData, sampleWithNewData, sampleWithPartialData, sampleWithFullData } from '../competencia.test-samples';

import { CompetenciaService } from './competencia.service';

const requireRestSample: ICompetencia = {
  ...sampleWithRequiredData,
};

describe('Competencia Service', () => {
  let service: CompetenciaService;
  let httpMock: HttpTestingController;
  let expectedResult: ICompetencia | ICompetencia[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [provideHttpClient(), provideHttpClientTesting()],
    });
    expectedResult = null;
    service = TestBed.inject(CompetenciaService);
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

    it('should create a Competencia', () => {
      const competencia = { ...sampleWithNewData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.create(competencia).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a Competencia', () => {
      const competencia = { ...sampleWithRequiredData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.update(competencia).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a Competencia', () => {
      const patchObject = { ...sampleWithPartialData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of Competencia', () => {
      const returnedFromService = { ...requireRestSample };

      const expected = { ...sampleWithRequiredData };

      service.query().subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush([returnedFromService]);
      httpMock.verify();
      expect(expectedResult).toMatchObject([expected]);
    });

    it('should delete a Competencia', () => {
      const expected = true;

      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult).toBe(expected);
    });

    describe('addCompetenciaToCollectionIfMissing', () => {
      it('should add a Competencia to an empty array', () => {
        const competencia: ICompetencia = sampleWithRequiredData;
        expectedResult = service.addCompetenciaToCollectionIfMissing([], competencia);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(competencia);
      });

      it('should not add a Competencia to an array that contains it', () => {
        const competencia: ICompetencia = sampleWithRequiredData;
        const competenciaCollection: ICompetencia[] = [
          {
            ...competencia,
          },
          sampleWithPartialData,
        ];
        expectedResult = service.addCompetenciaToCollectionIfMissing(competenciaCollection, competencia);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a Competencia to an array that doesn't contain it", () => {
        const competencia: ICompetencia = sampleWithRequiredData;
        const competenciaCollection: ICompetencia[] = [sampleWithPartialData];
        expectedResult = service.addCompetenciaToCollectionIfMissing(competenciaCollection, competencia);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(competencia);
      });

      it('should add only unique Competencia to an array', () => {
        const competenciaArray: ICompetencia[] = [sampleWithRequiredData, sampleWithPartialData, sampleWithFullData];
        const competenciaCollection: ICompetencia[] = [sampleWithRequiredData];
        expectedResult = service.addCompetenciaToCollectionIfMissing(competenciaCollection, ...competenciaArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const competencia: ICompetencia = sampleWithRequiredData;
        const competencia2: ICompetencia = sampleWithPartialData;
        expectedResult = service.addCompetenciaToCollectionIfMissing([], competencia, competencia2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(competencia);
        expect(expectedResult).toContain(competencia2);
      });

      it('should accept null and undefined values', () => {
        const competencia: ICompetencia = sampleWithRequiredData;
        expectedResult = service.addCompetenciaToCollectionIfMissing([], null, competencia, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(competencia);
      });

      it('should return initial array if no Competencia is added', () => {
        const competenciaCollection: ICompetencia[] = [sampleWithRequiredData];
        expectedResult = service.addCompetenciaToCollectionIfMissing(competenciaCollection, undefined, null);
        expect(expectedResult).toEqual(competenciaCollection);
      });
    });

    describe('compareCompetencia', () => {
      it('Should return true if both entities are null', () => {
        const entity1 = null;
        const entity2 = null;

        const compareResult = service.compareCompetencia(entity1, entity2);

        expect(compareResult).toEqual(true);
      });

      it('Should return false if one entity is null', () => {
        const entity1 = { id: 123 };
        const entity2 = null;

        const compareResult1 = service.compareCompetencia(entity1, entity2);
        const compareResult2 = service.compareCompetencia(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey differs', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 456 };

        const compareResult1 = service.compareCompetencia(entity1, entity2);
        const compareResult2 = service.compareCompetencia(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey matches', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 123 };

        const compareResult1 = service.compareCompetencia(entity1, entity2);
        const compareResult2 = service.compareCompetencia(entity2, entity1);

        expect(compareResult1).toEqual(true);
        expect(compareResult2).toEqual(true);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
