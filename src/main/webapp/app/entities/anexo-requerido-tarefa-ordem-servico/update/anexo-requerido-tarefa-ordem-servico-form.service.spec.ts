import { TestBed } from '@angular/core/testing';

import { sampleWithRequiredData, sampleWithNewData } from '../anexo-requerido-tarefa-ordem-servico.test-samples';

import { AnexoRequeridoTarefaOrdemServicoFormService } from './anexo-requerido-tarefa-ordem-servico-form.service';

describe('AnexoRequeridoTarefaOrdemServico Form Service', () => {
  let service: AnexoRequeridoTarefaOrdemServicoFormService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(AnexoRequeridoTarefaOrdemServicoFormService);
  });

  describe('Service methods', () => {
    describe('createAnexoRequeridoTarefaOrdemServicoFormGroup', () => {
      it('should create a new form with FormControl', () => {
        const formGroup = service.createAnexoRequeridoTarefaOrdemServicoFormGroup();

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            obrigatorio: expect.any(Object),
            anexoRequerido: expect.any(Object),
            tarefaOrdemServico: expect.any(Object),
          }),
        );
      });

      it('passing IAnexoRequeridoTarefaOrdemServico should create a new form with FormGroup', () => {
        const formGroup = service.createAnexoRequeridoTarefaOrdemServicoFormGroup(sampleWithRequiredData);

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            obrigatorio: expect.any(Object),
            anexoRequerido: expect.any(Object),
            tarefaOrdemServico: expect.any(Object),
          }),
        );
      });
    });

    describe('getAnexoRequeridoTarefaOrdemServico', () => {
      it('should return NewAnexoRequeridoTarefaOrdemServico for default AnexoRequeridoTarefaOrdemServico initial value', () => {
        const formGroup = service.createAnexoRequeridoTarefaOrdemServicoFormGroup(sampleWithNewData);

        const anexoRequeridoTarefaOrdemServico = service.getAnexoRequeridoTarefaOrdemServico(formGroup) as any;

        expect(anexoRequeridoTarefaOrdemServico).toMatchObject(sampleWithNewData);
      });

      it('should return NewAnexoRequeridoTarefaOrdemServico for empty AnexoRequeridoTarefaOrdemServico initial value', () => {
        const formGroup = service.createAnexoRequeridoTarefaOrdemServicoFormGroup();

        const anexoRequeridoTarefaOrdemServico = service.getAnexoRequeridoTarefaOrdemServico(formGroup) as any;

        expect(anexoRequeridoTarefaOrdemServico).toMatchObject({});
      });

      it('should return IAnexoRequeridoTarefaOrdemServico', () => {
        const formGroup = service.createAnexoRequeridoTarefaOrdemServicoFormGroup(sampleWithRequiredData);

        const anexoRequeridoTarefaOrdemServico = service.getAnexoRequeridoTarefaOrdemServico(formGroup) as any;

        expect(anexoRequeridoTarefaOrdemServico).toMatchObject(sampleWithRequiredData);
      });
    });

    describe('resetForm', () => {
      it('passing IAnexoRequeridoTarefaOrdemServico should not enable id FormControl', () => {
        const formGroup = service.createAnexoRequeridoTarefaOrdemServicoFormGroup();
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, sampleWithRequiredData);

        expect(formGroup.controls.id.disabled).toBe(true);
      });

      it('passing NewAnexoRequeridoTarefaOrdemServico should disable id FormControl', () => {
        const formGroup = service.createAnexoRequeridoTarefaOrdemServicoFormGroup(sampleWithRequiredData);
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, { id: null });

        expect(formGroup.controls.id.disabled).toBe(true);
      });
    });
  });
});
