import { TestBed } from '@angular/core/testing';

import { sampleWithRequiredData, sampleWithNewData } from '../fluxo-execucao.test-samples';

import { FluxoExecucaoFormService } from './fluxo-execucao-form.service';

describe('FluxoExecucao Form Service', () => {
  let service: FluxoExecucaoFormService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(FluxoExecucaoFormService);
  });

  describe('Service methods', () => {
    describe('createFluxoExecucaoFormGroup', () => {
      it('should create a new form with FormControl', () => {
        const formGroup = service.createFluxoExecucaoFormGroup();

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            nome: expect.any(Object),
            descricao: expect.any(Object),
          }),
        );
      });

      it('passing IFluxoExecucao should create a new form with FormGroup', () => {
        const formGroup = service.createFluxoExecucaoFormGroup(sampleWithRequiredData);

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            nome: expect.any(Object),
            descricao: expect.any(Object),
          }),
        );
      });
    });

    describe('getFluxoExecucao', () => {
      it('should return NewFluxoExecucao for default FluxoExecucao initial value', () => {
        const formGroup = service.createFluxoExecucaoFormGroup(sampleWithNewData);

        const fluxoExecucao = service.getFluxoExecucao(formGroup) as any;

        expect(fluxoExecucao).toMatchObject(sampleWithNewData);
      });

      it('should return NewFluxoExecucao for empty FluxoExecucao initial value', () => {
        const formGroup = service.createFluxoExecucaoFormGroup();

        const fluxoExecucao = service.getFluxoExecucao(formGroup) as any;

        expect(fluxoExecucao).toMatchObject({});
      });

      it('should return IFluxoExecucao', () => {
        const formGroup = service.createFluxoExecucaoFormGroup(sampleWithRequiredData);

        const fluxoExecucao = service.getFluxoExecucao(formGroup) as any;

        expect(fluxoExecucao).toMatchObject(sampleWithRequiredData);
      });
    });

    describe('resetForm', () => {
      it('passing IFluxoExecucao should not enable id FormControl', () => {
        const formGroup = service.createFluxoExecucaoFormGroup();
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, sampleWithRequiredData);

        expect(formGroup.controls.id.disabled).toBe(true);
      });

      it('passing NewFluxoExecucao should disable id FormControl', () => {
        const formGroup = service.createFluxoExecucaoFormGroup(sampleWithRequiredData);
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, { id: null });

        expect(formGroup.controls.id.disabled).toBe(true);
      });
    });
  });
});
