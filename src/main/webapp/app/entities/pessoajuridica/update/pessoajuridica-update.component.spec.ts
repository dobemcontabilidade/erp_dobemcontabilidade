import { ComponentFixture, TestBed } from '@angular/core/testing';
import { provideHttpClient, HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { of, Subject, from } from 'rxjs';

import { PessoajuridicaService } from '../service/pessoajuridica.service';
import { IPessoajuridica } from '../pessoajuridica.model';
import { PessoajuridicaFormService } from './pessoajuridica-form.service';

import { PessoajuridicaUpdateComponent } from './pessoajuridica-update.component';

describe('Pessoajuridica Management Update Component', () => {
  let comp: PessoajuridicaUpdateComponent;
  let fixture: ComponentFixture<PessoajuridicaUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let pessoajuridicaFormService: PessoajuridicaFormService;
  let pessoajuridicaService: PessoajuridicaService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [PessoajuridicaUpdateComponent],
      providers: [
        provideHttpClient(),
        FormBuilder,
        {
          provide: ActivatedRoute,
          useValue: {
            params: from([{}]),
          },
        },
      ],
    })
      .overrideTemplate(PessoajuridicaUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(PessoajuridicaUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    pessoajuridicaFormService = TestBed.inject(PessoajuridicaFormService);
    pessoajuridicaService = TestBed.inject(PessoajuridicaService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should update editForm', () => {
      const pessoajuridica: IPessoajuridica = { id: 456 };

      activatedRoute.data = of({ pessoajuridica });
      comp.ngOnInit();

      expect(comp.pessoajuridica).toEqual(pessoajuridica);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IPessoajuridica>>();
      const pessoajuridica = { id: 123 };
      jest.spyOn(pessoajuridicaFormService, 'getPessoajuridica').mockReturnValue(pessoajuridica);
      jest.spyOn(pessoajuridicaService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ pessoajuridica });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: pessoajuridica }));
      saveSubject.complete();

      // THEN
      expect(pessoajuridicaFormService.getPessoajuridica).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(pessoajuridicaService.update).toHaveBeenCalledWith(expect.objectContaining(pessoajuridica));
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IPessoajuridica>>();
      const pessoajuridica = { id: 123 };
      jest.spyOn(pessoajuridicaFormService, 'getPessoajuridica').mockReturnValue({ id: null });
      jest.spyOn(pessoajuridicaService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ pessoajuridica: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: pessoajuridica }));
      saveSubject.complete();

      // THEN
      expect(pessoajuridicaFormService.getPessoajuridica).toHaveBeenCalled();
      expect(pessoajuridicaService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IPessoajuridica>>();
      const pessoajuridica = { id: 123 };
      jest.spyOn(pessoajuridicaService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ pessoajuridica });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(pessoajuridicaService.update).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });
});
