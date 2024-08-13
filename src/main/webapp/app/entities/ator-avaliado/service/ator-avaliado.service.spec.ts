import { TestBed } from '@angular/core/testing';
import { provideHttpClientTesting, HttpTestingController } from '@angular/common/http/testing';
import { provideHttpClient } from '@angular/common/http';

import { IAtorAvaliado } from '../ator-avaliado.model';
import { sampleWithRequiredData, sampleWithNewData, sampleWithPartialData, sampleWithFullData } from '../ator-avaliado.test-samples';

import { AtorAvaliadoService } from './ator-avaliado.service';

const requireRestSample: IAtorAvaliado = {
  ...sampleWithRequiredData,
};

describe('AtorAvaliado Service', () => {
  let service: AtorAvaliadoService;
  let httpMock: HttpTestingController;
  let expectedResult: IAtorAvaliado | IAtorAvaliado[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [provideHttpClient(), provideHttpClientTesting()],
    });
    expectedResult = null;
    service = TestBed.inject(AtorAvaliadoService);
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

    it('should create a AtorAvaliado', () => {
      const atorAvaliado = { ...sampleWithNewData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.create(atorAvaliado).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a AtorAvaliado', () => {
      const atorAvaliado = { ...sampleWithRequiredData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.update(atorAvaliado).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a AtorAvaliado', () => {
      const patchObject = { ...sampleWithPartialData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of AtorAvaliado', () => {
      const returnedFromService = { ...requireRestSample };

      const expected = { ...sampleWithRequiredData };

      service.query().subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush([returnedFromService]);
      httpMock.verify();
      expect(expectedResult).toMatchObject([expected]);
    });

    it('should delete a AtorAvaliado', () => {
      const expected = true;

      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult).toBe(expected);
    });

    describe('addAtorAvaliadoToCollectionIfMissing', () => {
      it('should add a AtorAvaliado to an empty array', () => {
        const atorAvaliado: IAtorAvaliado = sampleWithRequiredData;
        expectedResult = service.addAtorAvaliadoToCollectionIfMissing([], atorAvaliado);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(atorAvaliado);
      });

      it('should not add a AtorAvaliado to an array that contains it', () => {
        const atorAvaliado: IAtorAvaliado = sampleWithRequiredData;
        const atorAvaliadoCollection: IAtorAvaliado[] = [
          {
            ...atorAvaliado,
          },
          sampleWithPartialData,
        ];
        expectedResult = service.addAtorAvaliadoToCollectionIfMissing(atorAvaliadoCollection, atorAvaliado);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a AtorAvaliado to an array that doesn't contain it", () => {
        const atorAvaliado: IAtorAvaliado = sampleWithRequiredData;
        const atorAvaliadoCollection: IAtorAvaliado[] = [sampleWithPartialData];
        expectedResult = service.addAtorAvaliadoToCollectionIfMissing(atorAvaliadoCollection, atorAvaliado);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(atorAvaliado);
      });

      it('should add only unique AtorAvaliado to an array', () => {
        const atorAvaliadoArray: IAtorAvaliado[] = [sampleWithRequiredData, sampleWithPartialData, sampleWithFullData];
        const atorAvaliadoCollection: IAtorAvaliado[] = [sampleWithRequiredData];
        expectedResult = service.addAtorAvaliadoToCollectionIfMissing(atorAvaliadoCollection, ...atorAvaliadoArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const atorAvaliado: IAtorAvaliado = sampleWithRequiredData;
        const atorAvaliado2: IAtorAvaliado = sampleWithPartialData;
        expectedResult = service.addAtorAvaliadoToCollectionIfMissing([], atorAvaliado, atorAvaliado2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(atorAvaliado);
        expect(expectedResult).toContain(atorAvaliado2);
      });

      it('should accept null and undefined values', () => {
        const atorAvaliado: IAtorAvaliado = sampleWithRequiredData;
        expectedResult = service.addAtorAvaliadoToCollectionIfMissing([], null, atorAvaliado, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(atorAvaliado);
      });

      it('should return initial array if no AtorAvaliado is added', () => {
        const atorAvaliadoCollection: IAtorAvaliado[] = [sampleWithRequiredData];
        expectedResult = service.addAtorAvaliadoToCollectionIfMissing(atorAvaliadoCollection, undefined, null);
        expect(expectedResult).toEqual(atorAvaliadoCollection);
      });
    });

    describe('compareAtorAvaliado', () => {
      it('Should return true if both entities are null', () => {
        const entity1 = null;
        const entity2 = null;

        const compareResult = service.compareAtorAvaliado(entity1, entity2);

        expect(compareResult).toEqual(true);
      });

      it('Should return false if one entity is null', () => {
        const entity1 = { id: 123 };
        const entity2 = null;

        const compareResult1 = service.compareAtorAvaliado(entity1, entity2);
        const compareResult2 = service.compareAtorAvaliado(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey differs', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 456 };

        const compareResult1 = service.compareAtorAvaliado(entity1, entity2);
        const compareResult2 = service.compareAtorAvaliado(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey matches', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 123 };

        const compareResult1 = service.compareAtorAvaliado(entity1, entity2);
        const compareResult2 = service.compareAtorAvaliado(entity2, entity1);

        expect(compareResult1).toEqual(true);
        expect(compareResult2).toEqual(true);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
