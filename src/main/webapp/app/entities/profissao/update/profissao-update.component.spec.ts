import { ComponentFixture, TestBed } from '@angular/core/testing';
import { provideHttpClient, HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { of, Subject, from } from 'rxjs';

import { ProfissaoService } from '../service/profissao.service';
import { IProfissao } from '../profissao.model';
import { ProfissaoFormService } from './profissao-form.service';

import { ProfissaoUpdateComponent } from './profissao-update.component';

describe('Profissao Management Update Component', () => {
  let comp: ProfissaoUpdateComponent;
  let fixture: ComponentFixture<ProfissaoUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let profissaoFormService: ProfissaoFormService;
  let profissaoService: ProfissaoService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [ProfissaoUpdateComponent],
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
      .overrideTemplate(ProfissaoUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(ProfissaoUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    profissaoFormService = TestBed.inject(ProfissaoFormService);
    profissaoService = TestBed.inject(ProfissaoService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should update editForm', () => {
      const profissao: IProfissao = { id: 456 };

      activatedRoute.data = of({ profissao });
      comp.ngOnInit();

      expect(comp.profissao).toEqual(profissao);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IProfissao>>();
      const profissao = { id: 123 };
      jest.spyOn(profissaoFormService, 'getProfissao').mockReturnValue(profissao);
      jest.spyOn(profissaoService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ profissao });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: profissao }));
      saveSubject.complete();

      // THEN
      expect(profissaoFormService.getProfissao).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(profissaoService.update).toHaveBeenCalledWith(expect.objectContaining(profissao));
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IProfissao>>();
      const profissao = { id: 123 };
      jest.spyOn(profissaoFormService, 'getProfissao').mockReturnValue({ id: null });
      jest.spyOn(profissaoService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ profissao: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: profissao }));
      saveSubject.complete();

      // THEN
      expect(profissaoFormService.getProfissao).toHaveBeenCalled();
      expect(profissaoService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IProfissao>>();
      const profissao = { id: 123 };
      jest.spyOn(profissaoService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ profissao });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(profissaoService.update).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });
});
