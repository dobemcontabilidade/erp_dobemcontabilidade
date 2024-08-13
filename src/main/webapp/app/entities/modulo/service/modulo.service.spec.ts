import { TestBed } from '@angular/core/testing';
import { provideHttpClientTesting, HttpTestingController } from '@angular/common/http/testing';
import { provideHttpClient } from '@angular/common/http';

import { IModulo } from '../modulo.model';
import { sampleWithRequiredData, sampleWithNewData, sampleWithPartialData, sampleWithFullData } from '../modulo.test-samples';

import { ModuloService } from './modulo.service';

const requireRestSample: IModulo = {
  ...sampleWithRequiredData,
};

describe('Modulo Service', () => {
  let service: ModuloService;
  let httpMock: HttpTestingController;
  let expectedResult: IModulo | IModulo[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [provideHttpClient(), provideHttpClientTesting()],
    });
    expectedResult = null;
    service = TestBed.inject(ModuloService);
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

    it('should create a Modulo', () => {
      const modulo = { ...sampleWithNewData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.create(modulo).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a Modulo', () => {
      const modulo = { ...sampleWithRequiredData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.update(modulo).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a Modulo', () => {
      const patchObject = { ...sampleWithPartialData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of Modulo', () => {
      const returnedFromService = { ...requireRestSample };

      const expected = { ...sampleWithRequiredData };

      service.query().subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush([returnedFromService]);
      httpMock.verify();
      expect(expectedResult).toMatchObject([expected]);
    });

    it('should delete a Modulo', () => {
      const expected = true;

      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult).toBe(expected);
    });

    describe('addModuloToCollectionIfMissing', () => {
      it('should add a Modulo to an empty array', () => {
        const modulo: IModulo = sampleWithRequiredData;
        expectedResult = service.addModuloToCollectionIfMissing([], modulo);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(modulo);
      });

      it('should not add a Modulo to an array that contains it', () => {
        const modulo: IModulo = sampleWithRequiredData;
        const moduloCollection: IModulo[] = [
          {
            ...modulo,
          },
          sampleWithPartialData,
        ];
        expectedResult = service.addModuloToCollectionIfMissing(moduloCollection, modulo);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a Modulo to an array that doesn't contain it", () => {
        const modulo: IModulo = sampleWithRequiredData;
        const moduloCollection: IModulo[] = [sampleWithPartialData];
        expectedResult = service.addModuloToCollectionIfMissing(moduloCollection, modulo);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(modulo);
      });

      it('should add only unique Modulo to an array', () => {
        const moduloArray: IModulo[] = [sampleWithRequiredData, sampleWithPartialData, sampleWithFullData];
        const moduloCollection: IModulo[] = [sampleWithRequiredData];
        expectedResult = service.addModuloToCollectionIfMissing(moduloCollection, ...moduloArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const modulo: IModulo = sampleWithRequiredData;
        const modulo2: IModulo = sampleWithPartialData;
        expectedResult = service.addModuloToCollectionIfMissing([], modulo, modulo2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(modulo);
        expect(expectedResult).toContain(modulo2);
      });

      it('should accept null and undefined values', () => {
        const modulo: IModulo = sampleWithRequiredData;
        expectedResult = service.addModuloToCollectionIfMissing([], null, modulo, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(modulo);
      });

      it('should return initial array if no Modulo is added', () => {
        const moduloCollection: IModulo[] = [sampleWithRequiredData];
        expectedResult = service.addModuloToCollectionIfMissing(moduloCollection, undefined, null);
        expect(expectedResult).toEqual(moduloCollection);
      });
    });

    describe('compareModulo', () => {
      it('Should return true if both entities are null', () => {
        const entity1 = null;
        const entity2 = null;

        const compareResult = service.compareModulo(entity1, entity2);

        expect(compareResult).toEqual(true);
      });

      it('Should return false if one entity is null', () => {
        const entity1 = { id: 123 };
        const entity2 = null;

        const compareResult1 = service.compareModulo(entity1, entity2);
        const compareResult2 = service.compareModulo(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey differs', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 456 };

        const compareResult1 = service.compareModulo(entity1, entity2);
        const compareResult2 = service.compareModulo(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey matches', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 123 };

        const compareResult1 = service.compareModulo(entity1, entity2);
        const compareResult2 = service.compareModulo(entity2, entity1);

        expect(compareResult1).toEqual(true);
        expect(compareResult2).toEqual(true);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
