import { TestBed } from '@angular/core/testing';
import { provideHttpClientTesting, HttpTestingController } from '@angular/common/http/testing';
import { provideHttpClient } from '@angular/common/http';

import { IEmpresaVinculada } from '../empresa-vinculada.model';
import { sampleWithRequiredData, sampleWithNewData, sampleWithPartialData, sampleWithFullData } from '../empresa-vinculada.test-samples';

import { EmpresaVinculadaService } from './empresa-vinculada.service';

const requireRestSample: IEmpresaVinculada = {
  ...sampleWithRequiredData,
};

describe('EmpresaVinculada Service', () => {
  let service: EmpresaVinculadaService;
  let httpMock: HttpTestingController;
  let expectedResult: IEmpresaVinculada | IEmpresaVinculada[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [provideHttpClient(), provideHttpClientTesting()],
    });
    expectedResult = null;
    service = TestBed.inject(EmpresaVinculadaService);
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

    it('should create a EmpresaVinculada', () => {
      const empresaVinculada = { ...sampleWithNewData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.create(empresaVinculada).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a EmpresaVinculada', () => {
      const empresaVinculada = { ...sampleWithRequiredData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.update(empresaVinculada).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a EmpresaVinculada', () => {
      const patchObject = { ...sampleWithPartialData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of EmpresaVinculada', () => {
      const returnedFromService = { ...requireRestSample };

      const expected = { ...sampleWithRequiredData };

      service.query().subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush([returnedFromService]);
      httpMock.verify();
      expect(expectedResult).toMatchObject([expected]);
    });

    it('should delete a EmpresaVinculada', () => {
      const expected = true;

      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult).toBe(expected);
    });

    describe('addEmpresaVinculadaToCollectionIfMissing', () => {
      it('should add a EmpresaVinculada to an empty array', () => {
        const empresaVinculada: IEmpresaVinculada = sampleWithRequiredData;
        expectedResult = service.addEmpresaVinculadaToCollectionIfMissing([], empresaVinculada);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(empresaVinculada);
      });

      it('should not add a EmpresaVinculada to an array that contains it', () => {
        const empresaVinculada: IEmpresaVinculada = sampleWithRequiredData;
        const empresaVinculadaCollection: IEmpresaVinculada[] = [
          {
            ...empresaVinculada,
          },
          sampleWithPartialData,
        ];
        expectedResult = service.addEmpresaVinculadaToCollectionIfMissing(empresaVinculadaCollection, empresaVinculada);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a EmpresaVinculada to an array that doesn't contain it", () => {
        const empresaVinculada: IEmpresaVinculada = sampleWithRequiredData;
        const empresaVinculadaCollection: IEmpresaVinculada[] = [sampleWithPartialData];
        expectedResult = service.addEmpresaVinculadaToCollectionIfMissing(empresaVinculadaCollection, empresaVinculada);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(empresaVinculada);
      });

      it('should add only unique EmpresaVinculada to an array', () => {
        const empresaVinculadaArray: IEmpresaVinculada[] = [sampleWithRequiredData, sampleWithPartialData, sampleWithFullData];
        const empresaVinculadaCollection: IEmpresaVinculada[] = [sampleWithRequiredData];
        expectedResult = service.addEmpresaVinculadaToCollectionIfMissing(empresaVinculadaCollection, ...empresaVinculadaArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const empresaVinculada: IEmpresaVinculada = sampleWithRequiredData;
        const empresaVinculada2: IEmpresaVinculada = sampleWithPartialData;
        expectedResult = service.addEmpresaVinculadaToCollectionIfMissing([], empresaVinculada, empresaVinculada2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(empresaVinculada);
        expect(expectedResult).toContain(empresaVinculada2);
      });

      it('should accept null and undefined values', () => {
        const empresaVinculada: IEmpresaVinculada = sampleWithRequiredData;
        expectedResult = service.addEmpresaVinculadaToCollectionIfMissing([], null, empresaVinculada, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(empresaVinculada);
      });

      it('should return initial array if no EmpresaVinculada is added', () => {
        const empresaVinculadaCollection: IEmpresaVinculada[] = [sampleWithRequiredData];
        expectedResult = service.addEmpresaVinculadaToCollectionIfMissing(empresaVinculadaCollection, undefined, null);
        expect(expectedResult).toEqual(empresaVinculadaCollection);
      });
    });

    describe('compareEmpresaVinculada', () => {
      it('Should return true if both entities are null', () => {
        const entity1 = null;
        const entity2 = null;

        const compareResult = service.compareEmpresaVinculada(entity1, entity2);

        expect(compareResult).toEqual(true);
      });

      it('Should return false if one entity is null', () => {
        const entity1 = { id: 123 };
        const entity2 = null;

        const compareResult1 = service.compareEmpresaVinculada(entity1, entity2);
        const compareResult2 = service.compareEmpresaVinculada(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey differs', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 456 };

        const compareResult1 = service.compareEmpresaVinculada(entity1, entity2);
        const compareResult2 = service.compareEmpresaVinculada(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey matches', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 123 };

        const compareResult1 = service.compareEmpresaVinculada(entity1, entity2);
        const compareResult2 = service.compareEmpresaVinculada(entity2, entity1);

        expect(compareResult1).toEqual(true);
        expect(compareResult2).toEqual(true);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
