import { TestBed } from '@angular/core/testing';

import { sampleWithRequiredData, sampleWithNewData } from '../anexo-ordem-servico-execucao.test-samples';

import { AnexoOrdemServicoExecucaoFormService } from './anexo-ordem-servico-execucao-form.service';

describe('AnexoOrdemServicoExecucao Form Service', () => {
  let service: AnexoOrdemServicoExecucaoFormService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(AnexoOrdemServicoExecucaoFormService);
  });

  describe('Service methods', () => {
    describe('createAnexoOrdemServicoExecucaoFormGroup', () => {
      it('should create a new form with FormControl', () => {
        const formGroup = service.createAnexoOrdemServicoExecucaoFormGroup();

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            url: expect.any(Object),
            descricao: expect.any(Object),
            dataHoraUpload: expect.any(Object),
            tarefaOrdemServicoExecucao: expect.any(Object),
          }),
        );
      });

      it('passing IAnexoOrdemServicoExecucao should create a new form with FormGroup', () => {
        const formGroup = service.createAnexoOrdemServicoExecucaoFormGroup(sampleWithRequiredData);

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            url: expect.any(Object),
            descricao: expect.any(Object),
            dataHoraUpload: expect.any(Object),
            tarefaOrdemServicoExecucao: expect.any(Object),
          }),
        );
      });
    });

    describe('getAnexoOrdemServicoExecucao', () => {
      it('should return NewAnexoOrdemServicoExecucao for default AnexoOrdemServicoExecucao initial value', () => {
        const formGroup = service.createAnexoOrdemServicoExecucaoFormGroup(sampleWithNewData);

        const anexoOrdemServicoExecucao = service.getAnexoOrdemServicoExecucao(formGroup) as any;

        expect(anexoOrdemServicoExecucao).toMatchObject(sampleWithNewData);
      });

      it('should return NewAnexoOrdemServicoExecucao for empty AnexoOrdemServicoExecucao initial value', () => {
        const formGroup = service.createAnexoOrdemServicoExecucaoFormGroup();

        const anexoOrdemServicoExecucao = service.getAnexoOrdemServicoExecucao(formGroup) as any;

        expect(anexoOrdemServicoExecucao).toMatchObject({});
      });

      it('should return IAnexoOrdemServicoExecucao', () => {
        const formGroup = service.createAnexoOrdemServicoExecucaoFormGroup(sampleWithRequiredData);

        const anexoOrdemServicoExecucao = service.getAnexoOrdemServicoExecucao(formGroup) as any;

        expect(anexoOrdemServicoExecucao).toMatchObject(sampleWithRequiredData);
      });
    });

    describe('resetForm', () => {
      it('passing IAnexoOrdemServicoExecucao should not enable id FormControl', () => {
        const formGroup = service.createAnexoOrdemServicoExecucaoFormGroup();
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, sampleWithRequiredData);

        expect(formGroup.controls.id.disabled).toBe(true);
      });

      it('passing NewAnexoOrdemServicoExecucao should disable id FormControl', () => {
        const formGroup = service.createAnexoOrdemServicoExecucaoFormGroup(sampleWithRequiredData);
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, { id: null });

        expect(formGroup.controls.id.disabled).toBe(true);
      });
    });
  });
});
