import { ComponentFixture, TestBed } from '@angular/core/testing';
import { provideRouter, withComponentInputBinding } from '@angular/router';
import { RouterTestingHarness } from '@angular/router/testing';
import { of } from 'rxjs';

import { AvaliacaoContadorDetailComponent } from './avaliacao-contador-detail.component';

describe('AvaliacaoContador Management Detail Component', () => {
  let comp: AvaliacaoContadorDetailComponent;
  let fixture: ComponentFixture<AvaliacaoContadorDetailComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [AvaliacaoContadorDetailComponent],
      providers: [
        provideRouter(
          [
            {
              path: '**',
              component: AvaliacaoContadorDetailComponent,
              resolve: { avaliacaoContador: () => of({ id: 123 }) },
            },
          ],
          withComponentInputBinding(),
        ),
      ],
    })
      .overrideTemplate(AvaliacaoContadorDetailComponent, '')
      .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(AvaliacaoContadorDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('Should load avaliacaoContador on init', async () => {
      const harness = await RouterTestingHarness.create();
      const instance = await harness.navigateByUrl('/', AvaliacaoContadorDetailComponent);

      // THEN
      expect(instance.avaliacaoContador()).toEqual(expect.objectContaining({ id: 123 }));
    });
  });

  describe('PreviousState', () => {
    it('Should navigate to previous state', () => {
      jest.spyOn(window.history, 'back');
      comp.previousState();
      expect(window.history.back).toHaveBeenCalled();
    });
  });
});
