import { TestBed } from '@angular/core/testing';
import { provideHttpClientTesting, HttpTestingController } from '@angular/common/http/testing';
import { provideHttpClient } from '@angular/common/http';

import { IAnexoRequerido } from '../anexo-requerido.model';
import { sampleWithRequiredData, sampleWithNewData, sampleWithPartialData, sampleWithFullData } from '../anexo-requerido.test-samples';

import { AnexoRequeridoService } from './anexo-requerido.service';

const requireRestSample: IAnexoRequerido = {
  ...sampleWithRequiredData,
};

describe('AnexoRequerido Service', () => {
  let service: AnexoRequeridoService;
  let httpMock: HttpTestingController;
  let expectedResult: IAnexoRequerido | IAnexoRequerido[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [provideHttpClient(), provideHttpClientTesting()],
    });
    expectedResult = null;
    service = TestBed.inject(AnexoRequeridoService);
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

    it('should create a AnexoRequerido', () => {
      const anexoRequerido = { ...sampleWithNewData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.create(anexoRequerido).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a AnexoRequerido', () => {
      const anexoRequerido = { ...sampleWithRequiredData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.update(anexoRequerido).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a AnexoRequerido', () => {
      const patchObject = { ...sampleWithPartialData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of AnexoRequerido', () => {
      const returnedFromService = { ...requireRestSample };

      const expected = { ...sampleWithRequiredData };

      service.query().subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush([returnedFromService]);
      httpMock.verify();
      expect(expectedResult).toMatchObject([expected]);
    });

    it('should delete a AnexoRequerido', () => {
      const expected = true;

      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult).toBe(expected);
    });

    describe('addAnexoRequeridoToCollectionIfMissing', () => {
      it('should add a AnexoRequerido to an empty array', () => {
        const anexoRequerido: IAnexoRequerido = sampleWithRequiredData;
        expectedResult = service.addAnexoRequeridoToCollectionIfMissing([], anexoRequerido);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(anexoRequerido);
      });

      it('should not add a AnexoRequerido to an array that contains it', () => {
        const anexoRequerido: IAnexoRequerido = sampleWithRequiredData;
        const anexoRequeridoCollection: IAnexoRequerido[] = [
          {
            ...anexoRequerido,
          },
          sampleWithPartialData,
        ];
        expectedResult = service.addAnexoRequeridoToCollectionIfMissing(anexoRequeridoCollection, anexoRequerido);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a AnexoRequerido to an array that doesn't contain it", () => {
        const anexoRequerido: IAnexoRequerido = sampleWithRequiredData;
        const anexoRequeridoCollection: IAnexoRequerido[] = [sampleWithPartialData];
        expectedResult = service.addAnexoRequeridoToCollectionIfMissing(anexoRequeridoCollection, anexoRequerido);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(anexoRequerido);
      });

      it('should add only unique AnexoRequerido to an array', () => {
        const anexoRequeridoArray: IAnexoRequerido[] = [sampleWithRequiredData, sampleWithPartialData, sampleWithFullData];
        const anexoRequeridoCollection: IAnexoRequerido[] = [sampleWithRequiredData];
        expectedResult = service.addAnexoRequeridoToCollectionIfMissing(anexoRequeridoCollection, ...anexoRequeridoArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const anexoRequerido: IAnexoRequerido = sampleWithRequiredData;
        const anexoRequerido2: IAnexoRequerido = sampleWithPartialData;
        expectedResult = service.addAnexoRequeridoToCollectionIfMissing([], anexoRequerido, anexoRequerido2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(anexoRequerido);
        expect(expectedResult).toContain(anexoRequerido2);
      });

      it('should accept null and undefined values', () => {
        const anexoRequerido: IAnexoRequerido = sampleWithRequiredData;
        expectedResult = service.addAnexoRequeridoToCollectionIfMissing([], null, anexoRequerido, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(anexoRequerido);
      });

      it('should return initial array if no AnexoRequerido is added', () => {
        const anexoRequeridoCollection: IAnexoRequerido[] = [sampleWithRequiredData];
        expectedResult = service.addAnexoRequeridoToCollectionIfMissing(anexoRequeridoCollection, undefined, null);
        expect(expectedResult).toEqual(anexoRequeridoCollection);
      });
    });

    describe('compareAnexoRequerido', () => {
      it('Should return true if both entities are null', () => {
        const entity1 = null;
        const entity2 = null;

        const compareResult = service.compareAnexoRequerido(entity1, entity2);

        expect(compareResult).toEqual(true);
      });

      it('Should return false if one entity is null', () => {
        const entity1 = { id: 123 };
        const entity2 = null;

        const compareResult1 = service.compareAnexoRequerido(entity1, entity2);
        const compareResult2 = service.compareAnexoRequerido(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey differs', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 456 };

        const compareResult1 = service.compareAnexoRequerido(entity1, entity2);
        const compareResult2 = service.compareAnexoRequerido(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey matches', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 123 };

        const compareResult1 = service.compareAnexoRequerido(entity1, entity2);
        const compareResult2 = service.compareAnexoRequerido(entity2, entity1);

        expect(compareResult1).toEqual(true);
        expect(compareResult2).toEqual(true);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
