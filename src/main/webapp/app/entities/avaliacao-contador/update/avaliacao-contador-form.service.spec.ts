import { TestBed } from '@angular/core/testing';

import { sampleWithRequiredData, sampleWithNewData } from '../avaliacao-contador.test-samples';

import { AvaliacaoContadorFormService } from './avaliacao-contador-form.service';

describe('AvaliacaoContador Form Service', () => {
  let service: AvaliacaoContadorFormService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(AvaliacaoContadorFormService);
  });

  describe('Service methods', () => {
    describe('createAvaliacaoContadorFormGroup', () => {
      it('should create a new form with FormControl', () => {
        const formGroup = service.createAvaliacaoContadorFormGroup();

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            pontuacao: expect.any(Object),
            contador: expect.any(Object),
            avaliacao: expect.any(Object),
          }),
        );
      });

      it('passing IAvaliacaoContador should create a new form with FormGroup', () => {
        const formGroup = service.createAvaliacaoContadorFormGroup(sampleWithRequiredData);

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            pontuacao: expect.any(Object),
            contador: expect.any(Object),
            avaliacao: expect.any(Object),
          }),
        );
      });
    });

    describe('getAvaliacaoContador', () => {
      it('should return NewAvaliacaoContador for default AvaliacaoContador initial value', () => {
        const formGroup = service.createAvaliacaoContadorFormGroup(sampleWithNewData);

        const avaliacaoContador = service.getAvaliacaoContador(formGroup) as any;

        expect(avaliacaoContador).toMatchObject(sampleWithNewData);
      });

      it('should return NewAvaliacaoContador for empty AvaliacaoContador initial value', () => {
        const formGroup = service.createAvaliacaoContadorFormGroup();

        const avaliacaoContador = service.getAvaliacaoContador(formGroup) as any;

        expect(avaliacaoContador).toMatchObject({});
      });

      it('should return IAvaliacaoContador', () => {
        const formGroup = service.createAvaliacaoContadorFormGroup(sampleWithRequiredData);

        const avaliacaoContador = service.getAvaliacaoContador(formGroup) as any;

        expect(avaliacaoContador).toMatchObject(sampleWithRequiredData);
      });
    });

    describe('resetForm', () => {
      it('passing IAvaliacaoContador should not enable id FormControl', () => {
        const formGroup = service.createAvaliacaoContadorFormGroup();
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, sampleWithRequiredData);

        expect(formGroup.controls.id.disabled).toBe(true);
      });

      it('passing NewAvaliacaoContador should disable id FormControl', () => {
        const formGroup = service.createAvaliacaoContadorFormGroup(sampleWithRequiredData);
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, { id: null });

        expect(formGroup.controls.id.disabled).toBe(true);
      });
    });
  });
});
