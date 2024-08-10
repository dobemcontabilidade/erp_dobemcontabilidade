import { ComponentFixture, TestBed } from '@angular/core/testing';
import { provideHttpClient, HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { of, Subject, from } from 'rxjs';

import { CompetenciaService } from '../service/competencia.service';
import { ICompetencia } from '../competencia.model';
import { CompetenciaFormService } from './competencia-form.service';

import { CompetenciaUpdateComponent } from './competencia-update.component';

describe('Competencia Management Update Component', () => {
  let comp: CompetenciaUpdateComponent;
  let fixture: ComponentFixture<CompetenciaUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let competenciaFormService: CompetenciaFormService;
  let competenciaService: CompetenciaService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [CompetenciaUpdateComponent],
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
      .overrideTemplate(CompetenciaUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(CompetenciaUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    competenciaFormService = TestBed.inject(CompetenciaFormService);
    competenciaService = TestBed.inject(CompetenciaService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should update editForm', () => {
      const competencia: ICompetencia = { id: 456 };

      activatedRoute.data = of({ competencia });
      comp.ngOnInit();

      expect(comp.competencia).toEqual(competencia);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<ICompetencia>>();
      const competencia = { id: 123 };
      jest.spyOn(competenciaFormService, 'getCompetencia').mockReturnValue(competencia);
      jest.spyOn(competenciaService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ competencia });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: competencia }));
      saveSubject.complete();

      // THEN
      expect(competenciaFormService.getCompetencia).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(competenciaService.update).toHaveBeenCalledWith(expect.objectContaining(competencia));
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<ICompetencia>>();
      const competencia = { id: 123 };
      jest.spyOn(competenciaFormService, 'getCompetencia').mockReturnValue({ id: null });
      jest.spyOn(competenciaService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ competencia: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: competencia }));
      saveSubject.complete();

      // THEN
      expect(competenciaFormService.getCompetencia).toHaveBeenCalled();
      expect(competenciaService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<ICompetencia>>();
      const competencia = { id: 123 };
      jest.spyOn(competenciaService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ competencia });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(competenciaService.update).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });
});
