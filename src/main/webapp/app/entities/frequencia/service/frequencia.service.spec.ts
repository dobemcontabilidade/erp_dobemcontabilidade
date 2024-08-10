import { TestBed } from '@angular/core/testing';
import { provideHttpClientTesting, HttpTestingController } from '@angular/common/http/testing';
import { provideHttpClient } from '@angular/common/http';

import { IFrequencia } from '../frequencia.model';
import { sampleWithRequiredData, sampleWithNewData, sampleWithPartialData, sampleWithFullData } from '../frequencia.test-samples';

import { FrequenciaService } from './frequencia.service';

const requireRestSample: IFrequencia = {
  ...sampleWithRequiredData,
};

describe('Frequencia Service', () => {
  let service: FrequenciaService;
  let httpMock: HttpTestingController;
  let expectedResult: IFrequencia | IFrequencia[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [provideHttpClient(), provideHttpClientTesting()],
    });
    expectedResult = null;
    service = TestBed.inject(FrequenciaService);
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

    it('should create a Frequencia', () => {
      const frequencia = { ...sampleWithNewData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.create(frequencia).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a Frequencia', () => {
      const frequencia = { ...sampleWithRequiredData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.update(frequencia).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a Frequencia', () => {
      const patchObject = { ...sampleWithPartialData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of Frequencia', () => {
      const returnedFromService = { ...requireRestSample };

      const expected = { ...sampleWithRequiredData };

      service.query().subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush([returnedFromService]);
      httpMock.verify();
      expect(expectedResult).toMatchObject([expected]);
    });

    it('should delete a Frequencia', () => {
      const expected = true;

      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult).toBe(expected);
    });

    describe('addFrequenciaToCollectionIfMissing', () => {
      it('should add a Frequencia to an empty array', () => {
        const frequencia: IFrequencia = sampleWithRequiredData;
        expectedResult = service.addFrequenciaToCollectionIfMissing([], frequencia);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(frequencia);
      });

      it('should not add a Frequencia to an array that contains it', () => {
        const frequencia: IFrequencia = sampleWithRequiredData;
        const frequenciaCollection: IFrequencia[] = [
          {
            ...frequencia,
          },
          sampleWithPartialData,
        ];
        expectedResult = service.addFrequenciaToCollectionIfMissing(frequenciaCollection, frequencia);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a Frequencia to an array that doesn't contain it", () => {
        const frequencia: IFrequencia = sampleWithRequiredData;
        const frequenciaCollection: IFrequencia[] = [sampleWithPartialData];
        expectedResult = service.addFrequenciaToCollectionIfMissing(frequenciaCollection, frequencia);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(frequencia);
      });

      it('should add only unique Frequencia to an array', () => {
        const frequenciaArray: IFrequencia[] = [sampleWithRequiredData, sampleWithPartialData, sampleWithFullData];
        const frequenciaCollection: IFrequencia[] = [sampleWithRequiredData];
        expectedResult = service.addFrequenciaToCollectionIfMissing(frequenciaCollection, ...frequenciaArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const frequencia: IFrequencia = sampleWithRequiredData;
        const frequencia2: IFrequencia = sampleWithPartialData;
        expectedResult = service.addFrequenciaToCollectionIfMissing([], frequencia, frequencia2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(frequencia);
        expect(expectedResult).toContain(frequencia2);
      });

      it('should accept null and undefined values', () => {
        const frequencia: IFrequencia = sampleWithRequiredData;
        expectedResult = service.addFrequenciaToCollectionIfMissing([], null, frequencia, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(frequencia);
      });

      it('should return initial array if no Frequencia is added', () => {
        const frequenciaCollection: IFrequencia[] = [sampleWithRequiredData];
        expectedResult = service.addFrequenciaToCollectionIfMissing(frequenciaCollection, undefined, null);
        expect(expectedResult).toEqual(frequenciaCollection);
      });
    });

    describe('compareFrequencia', () => {
      it('Should return true if both entities are null', () => {
        const entity1 = null;
        const entity2 = null;

        const compareResult = service.compareFrequencia(entity1, entity2);

        expect(compareResult).toEqual(true);
      });

      it('Should return false if one entity is null', () => {
        const entity1 = { id: 123 };
        const entity2 = null;

        const compareResult1 = service.compareFrequencia(entity1, entity2);
        const compareResult2 = service.compareFrequencia(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey differs', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 456 };

        const compareResult1 = service.compareFrequencia(entity1, entity2);
        const compareResult2 = service.compareFrequencia(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey matches', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 123 };

        const compareResult1 = service.compareFrequencia(entity1, entity2);
        const compareResult2 = service.compareFrequencia(entity2, entity1);

        expect(compareResult1).toEqual(true);
        expect(compareResult2).toEqual(true);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
