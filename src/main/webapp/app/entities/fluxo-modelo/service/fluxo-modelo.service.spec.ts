import { TestBed } from '@angular/core/testing';
import { provideHttpClientTesting, HttpTestingController } from '@angular/common/http/testing';
import { provideHttpClient } from '@angular/common/http';

import { IFluxoModelo } from '../fluxo-modelo.model';
import { sampleWithRequiredData, sampleWithNewData, sampleWithPartialData, sampleWithFullData } from '../fluxo-modelo.test-samples';

import { FluxoModeloService } from './fluxo-modelo.service';

const requireRestSample: IFluxoModelo = {
  ...sampleWithRequiredData,
};

describe('FluxoModelo Service', () => {
  let service: FluxoModeloService;
  let httpMock: HttpTestingController;
  let expectedResult: IFluxoModelo | IFluxoModelo[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [provideHttpClient(), provideHttpClientTesting()],
    });
    expectedResult = null;
    service = TestBed.inject(FluxoModeloService);
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

    it('should create a FluxoModelo', () => {
      const fluxoModelo = { ...sampleWithNewData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.create(fluxoModelo).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a FluxoModelo', () => {
      const fluxoModelo = { ...sampleWithRequiredData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.update(fluxoModelo).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a FluxoModelo', () => {
      const patchObject = { ...sampleWithPartialData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of FluxoModelo', () => {
      const returnedFromService = { ...requireRestSample };

      const expected = { ...sampleWithRequiredData };

      service.query().subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush([returnedFromService]);
      httpMock.verify();
      expect(expectedResult).toMatchObject([expected]);
    });

    it('should delete a FluxoModelo', () => {
      const expected = true;

      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult).toBe(expected);
    });

    describe('addFluxoModeloToCollectionIfMissing', () => {
      it('should add a FluxoModelo to an empty array', () => {
        const fluxoModelo: IFluxoModelo = sampleWithRequiredData;
        expectedResult = service.addFluxoModeloToCollectionIfMissing([], fluxoModelo);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(fluxoModelo);
      });

      it('should not add a FluxoModelo to an array that contains it', () => {
        const fluxoModelo: IFluxoModelo = sampleWithRequiredData;
        const fluxoModeloCollection: IFluxoModelo[] = [
          {
            ...fluxoModelo,
          },
          sampleWithPartialData,
        ];
        expectedResult = service.addFluxoModeloToCollectionIfMissing(fluxoModeloCollection, fluxoModelo);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a FluxoModelo to an array that doesn't contain it", () => {
        const fluxoModelo: IFluxoModelo = sampleWithRequiredData;
        const fluxoModeloCollection: IFluxoModelo[] = [sampleWithPartialData];
        expectedResult = service.addFluxoModeloToCollectionIfMissing(fluxoModeloCollection, fluxoModelo);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(fluxoModelo);
      });

      it('should add only unique FluxoModelo to an array', () => {
        const fluxoModeloArray: IFluxoModelo[] = [sampleWithRequiredData, sampleWithPartialData, sampleWithFullData];
        const fluxoModeloCollection: IFluxoModelo[] = [sampleWithRequiredData];
        expectedResult = service.addFluxoModeloToCollectionIfMissing(fluxoModeloCollection, ...fluxoModeloArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const fluxoModelo: IFluxoModelo = sampleWithRequiredData;
        const fluxoModelo2: IFluxoModelo = sampleWithPartialData;
        expectedResult = service.addFluxoModeloToCollectionIfMissing([], fluxoModelo, fluxoModelo2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(fluxoModelo);
        expect(expectedResult).toContain(fluxoModelo2);
      });

      it('should accept null and undefined values', () => {
        const fluxoModelo: IFluxoModelo = sampleWithRequiredData;
        expectedResult = service.addFluxoModeloToCollectionIfMissing([], null, fluxoModelo, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(fluxoModelo);
      });

      it('should return initial array if no FluxoModelo is added', () => {
        const fluxoModeloCollection: IFluxoModelo[] = [sampleWithRequiredData];
        expectedResult = service.addFluxoModeloToCollectionIfMissing(fluxoModeloCollection, undefined, null);
        expect(expectedResult).toEqual(fluxoModeloCollection);
      });
    });

    describe('compareFluxoModelo', () => {
      it('Should return true if both entities are null', () => {
        const entity1 = null;
        const entity2 = null;

        const compareResult = service.compareFluxoModelo(entity1, entity2);

        expect(compareResult).toEqual(true);
      });

      it('Should return false if one entity is null', () => {
        const entity1 = { id: 123 };
        const entity2 = null;

        const compareResult1 = service.compareFluxoModelo(entity1, entity2);
        const compareResult2 = service.compareFluxoModelo(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey differs', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 456 };

        const compareResult1 = service.compareFluxoModelo(entity1, entity2);
        const compareResult2 = service.compareFluxoModelo(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey matches', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 123 };

        const compareResult1 = service.compareFluxoModelo(entity1, entity2);
        const compareResult2 = service.compareFluxoModelo(entity2, entity1);

        expect(compareResult1).toEqual(true);
        expect(compareResult2).toEqual(true);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
